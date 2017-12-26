package model

import play.api.libs.functional.syntax._
import play.api.libs.json._


object User {

    implicit val JSON_USER_FORMATTER = (
        (__ \ "id").formatNullable[Long] ~
        (__ \ "avatarId").format[Long] ~
        (__ \ "fullName").format[String] ~
        (__ \ "emailAddress").format[String]
    )(jsonApply _, unlift(jsonUnapply))

    private[this] def jsonApply(id: Option[Long], avatarId: Long, fullName: String, emailAddress: String) = {
        new User(id, fullName, emailAddress)
    }

    private[this] def jsonUnapply(u: User): Option[(Option[Long], Long, String, String)] =
        Some( (u.id, u.id.map(_%8).getOrElse(0L), u.fullName, u.emailAddress) )
}

class User(val id: Option[Long], val fullName: String, val emailAddress: String) extends AnyModel {
    override type T = User

    def this(fullName: String, emailAddress: String) = this(None, fullName, emailAddress)
    def this(userId: Long) = this(Some(userId), null, null)

    override def inserted(id: Long): User = new User(Some(id), this.fullName, this.emailAddress)

    override def isInsertable: Boolean = !(this.fullName.isEmpty || this.emailAddress.isEmpty)
    override def isUpdatable: Boolean = this.isInsertable && this.id.isDefined

    override def toString: String =
        this.id.map(i => s"User(#${id})").getOrElse(s"User(${this.emailAddress})")
}
