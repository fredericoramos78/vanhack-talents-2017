package repo.impl

import java.sql.Connection
import java.time.LocalDateTime
import javax.inject.Inject

import anorm.SqlParser._
import anorm._
import model.{Topic, User}
import repo._
import utils.ExecutionContexts

import scala.concurrent.{ExecutionContext, Future}


trait MySQLTopicRepository extends AnyRDBMSRepository[Topic] {

    implicit val TOPIC_READER: RowParser[Topic] = {
        get[Long]("id") ~ get[LocalDateTime]("created_at") ~ get[String]("title") ~ get[String]("question") ~
            get[Long]("user_id") ~ get[String]("full_name") ~ get[String]("email_address") ~ get[Int]("comments_count") ~
            get[Option[LocalDateTime]]("last_commented_at") map {
            case id ~ createdAt ~ title ~ question ~ userId ~ fullName ~ emailAddress ~ commentCount ~ lastCommentedAt =>
                val user = new User(Some(userId), fullName, emailAddress)
                val topic = new Topic(Some(id), createdAt, title, question, user)
                topic.commentsCount = commentCount
                topic.latestComment = lastCommentedAt
                topic
        }
    }

    val INSERT_SQL = "INSERT INTO topic (user_id, title, question) VALUES ({userId}, {title}, {question})"
    override def insert(t: Topic)(implicit conn: Connection) = {
        t.owner.id.map { userId =>
            SQL(INSERT_SQL)
                .on('userId -> userId, 'title -> t.title, 'question -> t.question)
                .executeInsert[Option[Long]]()
        } getOrElse {
            throw new IllegalArgumentException("Cannot create a topic without indicating the owner")
        }
    }

    val SELECT_SQL =
        """SELECT id, created_at, title, question, user_id, full_name, email_address, comments_count, last_commented_at
          |FROM v_topics_and_users WHERE 1=1 """.stripMargin
    val WHERE_ID = "AND id={id}"
    val SELECT_BY_PK = SELECT_SQL.concat(WHERE_ID)
    override def select(id: Long)(implicit conn: Connection) = {
        SQL(SELECT_BY_PK)
            .on('id -> id)
            .as(TOPIC_READER.singleOpt)
    }
}

trait ElasticSearchTopicRepository extends KeywordBasedRepository[Topic] with AsyncRepository {

    override def searchByKeyword(keywords: Seq[String]) = Future { Seq.empty }
}

class TopicRepositoryImpl @Inject()(ec: ExecutionContexts) extends TopicRepository
    with MySQLTopicRepository
    with ElasticSearchTopicRepository {

    override implicit val threadPool: ExecutionContext = ec.ES_REPOS_THREADPOOL



    val SELECT_SORTBY_SQL =
        """SELECT id, created_at, title, question, user_id, full_name, email_address, comments_count, last_commented_at
          |FROM v_topics_and_users WHERE 1=1 """.stripMargin
    val WHERE_USERID = " AND user_id={userId} "
    val SELECT_SORTBY_WITH_USER_SQL = SELECT_SORTBY_SQL.concat(WHERE_USERID)

    override def selectSortedBy(field: String, user: Option[User], limit: Int)(implicit conn: Connection) = {
        val statement: String =
            (user.map(_ => SELECT_SORTBY_WITH_USER_SQL).getOrElse(SELECT_SORTBY_SQL))
                .concat(
                    if (field.equals(Topic.SORTBY_COMMENT_COUNT)) { " AND comments_count > 0 " }
                    else if (field.equals(Topic.SORTBY_LATEST_COMMENT)) { " AND last_commented_at IS NOT NULL " }
                    else { "" }
                )
                .concat(s" ORDER BY ${field} DESC")
                .concat(s" LIMIT ${limit}")
        SQL(statement)
            .on('userId -> user.flatMap(_.id))
            .as(TOPIC_READER.*)
    }

    val UPDATE_COUNTERS_SQL =
        """UPDATE topics SET
          |comments_count=comments_count+1, last_commented_at=:commentTimestamp
          |WHERE id={topicId} """.stripMargin

    override def updateCommentCounter(topicId: Long, commentTimestamp: LocalDateTime)(implicit conn: Connection) = {
        SQL(UPDATE_COUNTERS_SQL)
            .on('topicId -> topicId, 'commentTimestamp -> commentTimestamp)
            .executeUpdate() == 1
    }
}
