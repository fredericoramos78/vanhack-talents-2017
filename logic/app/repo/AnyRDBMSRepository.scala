package repo

import java.sql.Connection

import scala.concurrent.Future

trait AnyRDBMSRepository[T] {
    self: AsyncRepository =>

    def insert(t: T)(implicit conn: Connection): Future[Option[Long]]
    def select(id: Long)(implicit conn: Connection): Future[Option[T]]
}
