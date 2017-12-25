package repo

import model.Comment

import scala.concurrent.{ExecutionContext, Future}

trait CommentRepository extends AsyncRepository {
    def insert(comment: Comment, topicId: Long): Future[Option[Comment]]
    def selectByTopic(topicId: Long, offset: Int, limit: Int): Future[Seq[Comment]]
}

class DummyCommentRepository extends CommentRepository {
    override def insert(comment: Comment, topicId: Long): Future[Option[Comment]] = Future.successful(None)
    override def selectByTopic(topicId: Long, offset: Int, limit: Int): Future[Seq[Comment]] = Future.successful(Seq.empty)
    override implicit val threadPool: ExecutionContext = null
}
