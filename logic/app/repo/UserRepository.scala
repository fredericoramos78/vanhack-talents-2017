package repo

import java.sql.Connection

import model.User

import scala.concurrent.Future

trait UserRepository extends AnyRDBMSRepository[User] with AsyncRepository {

    def selectByEmail(email: String)(implicit conn: Connection): Future[Option[User]]
}
