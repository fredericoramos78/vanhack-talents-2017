package repo

import java.sql.Connection

trait AnyRDBMSRepository[T] {

    def insert(t: T)(implicit conn: Connection): Option[Long]

    def select(id: Long)(implicit conn: Connection): Option[T]
}
