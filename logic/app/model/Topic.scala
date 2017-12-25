package model

import java.time.LocalDateTime

import play.api.libs.functional.syntax._
import play.api.libs.json._


object Topic {

    val SORTBY_COMMENT_COUNT = "comment_count"
    val SORTBY_LATEST_COMMENT = "last_commented_at"

    implicit val JSON_TOPIC_FORMATTER = (
        (__ \ "id").formatNullable[Long] ~
        (__ \ "createdAt").format[LocalDateTime] ~
        (__ \ "title").format[String] ~
        (__ \ "owner").format[User](User.JSON_USER_FORMATTER) ~
        (__ \ "commentsCount").format[Int] ~
        (__ \ "latestComment").formatNullable[LocalDateTime]
    )(jsonApply _, unlift(jsonUnapply))

    private[this] def jsonApply(id: Option[Long], createdAt: LocalDateTime, title: String, owner: User,
                                commentsCount: Int, latestComment: Option[LocalDateTime]) = {
        val t = new Topic(id, createdAt, title, owner)
        t.commentsCount = commentsCount
        t.latestComment = latestComment
        t
    }

    private[this] def jsonUnapply(t: Topic): Option[(Option[Long], LocalDateTime, String, User, Int, Option[LocalDateTime])] =
        Some( (t.id, t.createdAt, t.title, t.owner, t.commentsCount, t.latestComment) )

    implicit val JSON_TOPIC_SEQ_FORMATTER = Format(Reads.seq(JSON_TOPIC_FORMATTER), Writes.seq(JSON_TOPIC_FORMATTER))
}

class Topic(val id: Option[Long], val createdAt: LocalDateTime, val title: String, val owner: User) extends AnyModel {
    override type T = Topic

    var commentsCount: Int = 0
    var latestComment: Option[LocalDateTime] = None


    def this(title: String, owner: User) = this(None, LocalDateTime.now(), title, owner)
    def this(title: String, ownerId: Long) = this(None, LocalDateTime.now(), title, new User(Some(ownerId), null, null))

    override def inserted(id: Long): Topic = new Topic(Some(id), this.createdAt, this.title, this.owner)

    override def isInsertable: Boolean = !this.title.isEmpty && this.owner.id.isDefined
    override def isUpdatable: Boolean = this.isInsertable && this.id.isDefined
}
