package utils

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext


@Singleton
class ExecutionContexts @Inject()(actorSystem: ActorSystem) {

    private[this] val CONTROLLERS_THREADPOOL_NAME = "app.thread-pools.controllers"
    private[this] val SERVICES_THREADPOOL_NAME = "app.thread-pools.services"
    private[this] val REPOS_THREADPOOL_NAME = "app.thread-pools.repos"

    val CONTROLLERS_THREADPOOL = new CustomExecutionContext(actorSystem, CONTROLLERS_THREADPOOL_NAME) {}
    val SERVICES_THREADPOOL = new CustomExecutionContext(actorSystem, SERVICES_THREADPOOL_NAME) {}
    val ES_REPOS_THREADPOOL = new CustomExecutionContext(actorSystem, REPOS_THREADPOOL_NAME) {}
}
