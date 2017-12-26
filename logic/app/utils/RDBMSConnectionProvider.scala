package utils

import java.sql.Connection
import javax.inject.Inject

import play.api.db.Database


trait ConnectionProvider[A] {

    def openTransaction(): A

    def commitTransaction(c: A): Unit

    def rollbackTransaction(c: A): Unit
}


class RDBMSConnectionProvider @Inject() (db: Database) extends ConnectionProvider[Connection] with Logger {

    def openTransaction(): Connection = db.getConnection(false)

    def commitTransaction(conn: Connection): Unit = {
        try {
            if (conn != null && conn.isClosed() == false && conn.getAutoCommit() == false) { conn.commit() }
        } finally {
            if (conn != null && conn.isClosed() == false) { conn.close() }
        }
    }

    def rollbackTransaction(conn: Connection): Unit = {
        try {
            if (conn != null && conn.isClosed() == false && conn.getAutoCommit() == false) { conn.rollback() }
        } finally {
            if (conn != null && conn.isClosed() == false) { conn.close() }
        }
    }
}