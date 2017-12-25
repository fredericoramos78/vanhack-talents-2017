package repo

import java.sql.Connection
import java.time.LocalDateTime

import model.{Topic, User}

import scala.concurrent.Future


trait TopicRepository extends AnyRDBMSRepository[Topic] with KeywordBasedRepository[Topic] with AsyncRepository {
    def selectSortedBy(field: String, user: Option[User] = None, limit: Int)(implicit conn: Connection): Future[Seq[Topic]]
    def updateCommentCounter(topicId: Long, commentTimestamp: LocalDateTime)(implicit conn: Connection): Future[Boolean]
}
