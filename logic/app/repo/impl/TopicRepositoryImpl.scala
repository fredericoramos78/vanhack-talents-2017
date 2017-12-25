package repo.impl

import java.sql.Connection
import java.time.LocalDateTime
import javax.inject.Inject

import anorm.SqlParser._
import anorm._
import model.{Topic, User}
import play.api.db.Database
import repo._
import utils.ExecutionContexts

import scala.concurrent.{ExecutionContext, Future}


trait MySQLTopicRepository extends AnyRDBMSRepository[Topic] with AsyncRepository {

    val db: Database



    implicit val TOPIC_READER: RowParser[Topic] = {
        get[Long]("id") ~ get[LocalDateTime]("created_at") ~ get[String]("title") ~ get[Long]("user_id") ~
            get[String]("full_name") ~ get[String]("email_address") ~ get[Int]("comment_count") ~
            get[Option[LocalDateTime]]("last_commented_at") map {
            case id ~ createdAt ~ title ~ userId ~ fullName ~ emailAddress ~ commentCount ~ lastCommentedAt =>
                val user = new User(Some(userId), fullName, emailAddress)
                val topic = new Topic(Some(id), createdAt, title, user)
                topic.commentsCount = commentCount
                topic.latestComment = lastCommentedAt
                topic
        }
    }

    val INSERT_SQL = "INSERT INTO topic (user_id, title) VALUES (:userId, :title)"
    override def insert(t: Topic)(implicit conn: Connection) = Future {
        t.owner.id.map { userId =>
            SQL(INSERT_SQL)
                .on('userId -> userId, 'title -> t.title)
                .executeInsert[Option[Long]]()
        } getOrElse {
            throw new IllegalArgumentException("Cannot create a topic without indicating the owner")
        }
    }

    val SELECT_SQL =
        """SELECT id, created_at, title, user_id, comment_count, last_commented_at
          |FROM v_topics_and_users WHERE 1=1 """.stripMargin
    val WHERE_ID = "AND id=:id"
    val SELECT_BY_PK = SELECT_SQL.concat(WHERE_ID)
    override def select(id: Long)(implicit conn: Connection) = Future {
        SQL(SELECT_BY_PK)
            .on('id -> id)
            .as(TOPIC_READER.singleOpt)
    }
}

trait ElasticSearchTopicRepository extends KeywordBasedRepository[Topic] with AsyncRepository {

    override def searchByKeyword(keywords: Seq[String]) = Future { Seq.empty }
}

class TopicRepositoryImpl @Inject()(_db: Database, ec: ExecutionContexts) extends TopicRepository
    with MySQLTopicRepository
    with ElasticSearchTopicRepository {

    override val db: Database = _db
    override implicit val threadPool: ExecutionContext = ec.REPOS_THREADPOOL


    val SELECT_SORTBY_SQL =
        """SELECT id, created_at, title, user_id, comment_count, last_commented_at
          |FROM v_topics_and_users WHERE 1=1 """.stripMargin
    val WHERE_USERID = " AND user_id=:userId "
    val SELECT_SORTBY_WITH_USER_SQL = SELECT_SORTBY_SQL.concat(WHERE_USERID)

    override def selectSortedBy(field: String, user: Option[User], limit: Int)(implicit conn: Connection) = Future {
        val statement: String =
            (user.map(_ => SELECT_SORTBY_WITH_USER_SQL).getOrElse(SELECT_SORTBY_SQL))
                .concat(s" ORDER BY ${field} DESC")
                .concat(s" LIMIT ${limit}")
        SQL(statement)
            .on('userId -> user.flatMap(_.id))
            .as(TOPIC_READER.*)
    }


    val UPDATE_COUNTERS_SQL =
        """UPDATE topics SET
          |comment_counter = comment_counter + 1, last_commented_at = :commentTimestamp
          |WHERE id=:topicId """.stripMargin

    override def updateCommentCounter(topicId: Long, commentTimestamp: LocalDateTime)(implicit conn: Connection) = Future {
        SQL(UPDATE_COUNTERS_SQL)
            .on('topicId -> topicId, 'commentTimestamp -> commentTimestamp)
            .executeUpdate() == 1
    }
}
