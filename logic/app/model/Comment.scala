package model

import java.time.LocalDateTime

import play.api.libs.functional.syntax._
import play.api.libs.json._

object Comment {

    implicit val JSON_COMMENT_FORMATTER = (
        (__ \ "id").formatNullable[Long] ~
        (__ \ "topicId").format[Long] ~
        (__ \ "user").format[User](User.JSON_USER_FORMATTER) ~
        (__ \ "createdAt").format[LocalDateTime] ~
        (__ \ "content").format[String]
    )(jsonApply _, unlift(jsonUnapply))

    private[this] def jsonApply(id: Option[Long], topicId: Long, user: User, createdAt: LocalDateTime, content: String) = {
        new Comment(id, topicId, user, createdAt, content)
    }

    private[this] def jsonUnapply(c: Comment): Option[(Option[Long], Long, User, LocalDateTime, String)] =
        Some( (c.id, c.topicId, c.user, c.createdAt, c.content) )

    implicit val JSON_COMMENT_SEQ_FORMATTER = Format(Reads.seq(JSON_COMMENT_FORMATTER), Writes.seq(JSON_COMMENT_FORMATTER))
}

class Comment(val id: Option[Long], val topicId: Long, val user: User, val createdAt: LocalDateTime, val content: String)
    extends AnyModel {
    override type T = Comment

    def this(topicId: Long, userId: Long, content: String) =
        this(None, topicId, new User(userId), LocalDateTime.now(), content)


    override def inserted(id: Long): Comment =
        new Comment(Some(id), this.topicId, this.user, this.createdAt, this.content)

    override def isInsertable: Boolean = !this.content.isEmpty && this.topicId > 0 && this.user.id.isDefined
    override def isUpdatable: Boolean = this.isInsertable && this.id.isDefined
}
