package services.impl

import javax.inject.Inject

import model.User
import play.api.db.Database
import repo.UserRepository
import services.UserService
import utils.{ExecutionContexts, Logger}

class UserServiceImpl @Inject()(repo: UserRepository, db: Database, ec: ExecutionContexts)
    extends UserService with Logger {

    implicit val threadPool = ec.SERVICES_THREADPOOL



    override def registerUser(user: User) = db.withTransaction { implicit c =>
        // cannot register the same email address more than once
        LOGGER.debug(s"[UserService.registerUser] checking for user ${user.emailAddress}")
        repo.selectByEmail(user.emailAddress).flatMap { maybeUser =>
            maybeUser match {
                case Some(_) =>
                    LOGGER.error(s"[UserService.registerUser] User ${user.emailAddress} already exists!")
                    throw new IllegalStateException(s"Emaill ${user.emailAddress} already registered")
                // email address not used. Registering new user...
                case None => repo.insert(user).map { maybeId =>
                    LOGGER.debug(s"[UserService.registerUser] Registering new user with email ${user.emailAddress}")
                    maybeId.map(user.inserted(_))
                        .getOrElse(throw new IllegalArgumentException(s"Could not register user ${user.emailAddress}"))
                }
            }
        }
    }

    override def searchUser(emailAddress: String) = db.withTransaction { implicit c =>
        LOGGER.debug(s"[UserService.searchUser] loading user with email $emailAddress}")
        repo.selectByEmail(emailAddress)
    }

    override def searchUser(userId: Long) = db.withTransaction { implicit c =>
        LOGGER.debug(s"[UserService.searchUser] loading user with id $userId}")
        repo.select(userId)
    }
}
