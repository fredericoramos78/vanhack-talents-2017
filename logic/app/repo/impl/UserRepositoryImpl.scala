package repo.impl

import java.sql.Connection

import anorm.SqlParser._
import anorm._
import model.User
import repo.UserRepository

class UserRepositoryImpl extends UserRepository {

    implicit val USER_READER: RowParser[User] = {
        get[Long]("id") ~  get[String]("full_name") ~ get[String]("email_address") map {
            case id ~ fullName ~ emailAddress => new User(Some(id), fullName, emailAddress)
        }
    }

    val INSERT_SQL = "INSERT INTO users (full_name, email_address) VALUES (:fullName, :emailAddress)"
    override def insert(u: User)(implicit conn: Connection) = {
        SQL(INSERT_SQL)
            .on('fullName -> u.fullName, 'emailAddress -> u.emailAddress)
            .executeInsert[Option[Long]]()
    }

    val SELECT_SQL = "SELECT id, full_name, email_address FROM users WHERE 1=1 "
    val WHERE_ID = "id=:id"
    val SELECT_BY_PK = SELECT_SQL.concat(WHERE_ID)
    override def select(id: Long)(implicit conn: Connection) = {
        SQL(SELECT_BY_PK)
            .on('id -> id)
            .as(USER_READER.singleOpt)
    }

    val WHERE_EMAIL = "email_address=:emailAddress"
    val SELECT_BY_EMAIL = SELECT_SQL.concat(WHERE_EMAIL)
    override def selectByEmail(email: String)(implicit conn: Connection) = {
        SQL(SELECT_BY_EMAIL)
            .on('emailAddress -> email)
            .as(USER_READER.singleOpt)
    }
}
