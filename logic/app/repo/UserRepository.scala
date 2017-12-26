package repo

import java.sql.Connection

import model.User

trait UserRepository extends AnyRDBMSRepository[User] {

    def selectByEmail(email: String)(implicit conn: Connection): Option[User]
}
