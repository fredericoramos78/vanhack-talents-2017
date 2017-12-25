package controllers

import javax.inject._

import play.api.Configuration
import play.api.mvc._


@Singleton
class IndexController @Inject() (mcc: MessagesControllerComponents, conf: Configuration) extends MessagesAbstractController(mcc) {


    def index = Action { request =>
        Redirect(routes.IndexController.version())
    }

    def version = Action { request =>
        Ok(conf.get[String]("app.version"))
    }
}