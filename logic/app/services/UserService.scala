package services

import model.User

import scala.concurrent.Future

trait UserService {

    def registerUser(user: User): Future[User]

    def searchUser(emailAddress: String): Future[Option[User]]

    def searchUser(userId: Long): Future[Option[User]]
}
