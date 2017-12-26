package modules

import java.sql.Connection

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import repo._
import repo.impl._
import services._
import services.impl._
import utils.{ConnectionProvider, ExecutionContexts, RDBMSConnectionProvider}

class AppModule extends AbstractModule with ScalaModule {
    override def configure = {
        // utils
        bind[ExecutionContexts].asEagerSingleton()
        bind[ConnectionProvider[Connection]].to[RDBMSConnectionProvider]
        // services
        bind[AuthenticatorService].to[AuthenticatorServiceImpl]
        bind[UserService].to[UserServiceImpl]
        bind[TopicService].to[TopicServiceImpl]
        // logics
        bind[TopicRepository].to[TopicRepositoryImpl]
        bind[UserRepository].to[UserRepositoryImpl]
        bind[CommentRepository].to[DummyCommentRepository]
        ()
    }
}
