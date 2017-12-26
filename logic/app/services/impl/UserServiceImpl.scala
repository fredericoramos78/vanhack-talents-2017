package services.impl

import java.sql.Connection
import javax.inject.Inject

import model.User
import repo.UserRepository
import services.UserService
import utils.{ConnectionProvider, ExecutionContexts, Logger}

import scala.concurrent.Future

class UserServiceImpl @Inject()(repo: UserRepository, cp: ConnectionProvider[Connection], ec: ExecutionContexts)
    extends UserService with Logger {

    implicit val threadPool = ec.SERVICES_THREADPOOL


    override def registerUser(user: User) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            // cannot register the same email address more than once
            LOGGER.debug(s"[UserService.registerUser] checking for user ${user.emailAddress}")
            repo.selectByEmail(user.emailAddress) match {
                case Some(_) =>
                    LOGGER.error(s"[UserService.registerUser] User ${user.emailAddress} already exists!")
                    throw new IllegalStateException(s"Emaill ${user.emailAddress} already registered")
                // email address not used. Registering new user...
                case None => repo.insert(user).map { id =>
                    LOGGER.debug(s"[UserService.registerUser] Registering new user with email ${user.emailAddress}")
                    user.inserted(id)
                    } getOrElse(throw new IllegalArgumentException(s"Could not register user ${user.emailAddress}"))
            }
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def searchUser(emailAddress: String) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[UserService.searchUser] loading user with email $emailAddress}")
            repo.selectByEmail(emailAddress)
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def searchUser(userId: Long) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[UserService.searchUser] loading user with id $userId}")
            repo.select(userId)
        } finally {
            cp.commitTransaction(c)
        }
    }
}
