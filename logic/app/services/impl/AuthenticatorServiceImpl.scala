package services.impl

import javax.inject.Inject

import play.api.db.Database
import repo.UserRepository
import services.AuthenticatorService
import utils.{ExecutionContexts, Logger}

class AuthenticatorServiceImpl @Inject() (repo: UserRepository, db: Database, ec: ExecutionContexts)
    extends AuthenticatorService with Logger {

    implicit val threadPool = ec.SERVICES_THREADPOOL



    override def authenticate(emailAddress: String, password: String) = db.withTransaction { implicit c =>
        LOGGER.debug(s"[AuthenticatorService.authenticate] Checking authentication for user #${emailAddress}")
        repo.selectByEmail(emailAddress).map { maybeUser =>
            maybeUser.map { u =>
                if ("password".equals(password)) {
                    LOGGER.debug(s"[AuthenticatorService.authenticate] User #${emailAddress} correctly authenticated")
                    u
                } else {
                    LOGGER.warn(s"[AuthenticatorService.authenticate] Incorrect password for user #${emailAddress}")
                    null
                }
            } getOrElse {
                LOGGER.warn(s"[AuthenticatorService.authenticate] User #${emailAddress} not registered")
                throw new IllegalStateException("Incorrect user or password")
            }
        }
    }
}
