package services

import model.User

import scala.concurrent.Future

trait AuthenticatorService {

    def authenticate(emailAddress: String, password: String): Future[User]
}
