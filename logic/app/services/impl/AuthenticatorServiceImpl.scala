package services.impl

import java.sql.Connection
import javax.inject.Inject

import repo.UserRepository
import services.AuthenticatorService
import utils.{ConnectionProvider, ExecutionContexts, Logger}

import scala.concurrent.Future

class AuthenticatorServiceImpl @Inject() (repo: UserRepository, cp: ConnectionProvider[Connection], ec: ExecutionContexts)
    extends AuthenticatorService with Logger {

    implicit val threadPool = ec.SERVICES_THREADPOOL



    override def authenticate(emailAddress: String, password: String) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[AuthenticatorService.authenticate] Checking authentication for user #${emailAddress}")
            repo.selectByEmail(emailAddress).map { u =>
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
        } finally {
            cp.commitTransaction(c)
        }
    }
}
