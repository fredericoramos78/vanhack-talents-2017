package controllers

import javax.inject._

import controllers.forms.{LoginForm, RegisterUserForm}
import model.User
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import services.{AuthenticatorService, UserService}
import utils.ExecutionContexts

import scala.concurrent.Future


@Singleton
class UsersController @Inject() (mcc: MessagesControllerComponents, conf: Configuration,
                                 ec: ExecutionContexts, userService: UserService, authService: AuthenticatorService)
    extends MessagesAbstractController(mcc) with AnyController {

    implicit val threadPool = ec.CONTROLLERS_THREADPOOL



    def putUser() = Action.async(parse.json) { request =>
        request.body.validate[RegisterUserForm](RegisterUserForm.JSON_READER).map { form =>
            userService.registerUser(new User(form.fullName, form.emailAddress)).map { u =>
                Ok(Json.toJson(u))
            } recover {
                case ex: Throwable => this.answerWithError(ex)
            }
        } recoverTotal {
            case error =>
                Future.successful(this.answerWithError("Could not read submitted form", error))
        }
    }

    def getUser(id: Long) = Action.async { request =>
        userService.searchUser(id).map { u =>
            Ok(Json.toJson(u))
        } recover {
            case ex: Throwable => this.answerWithError(ex)
        }
    }

    def postLogin() = Action.async(parse.json) { request =>
        request.body.validate[LoginForm](LoginForm.JSON_READER).map { form =>
            authService.authenticate(form.emailAddress, form.password).map { u =>
                Ok(Json.toJson(u))
            } recover {
                case ex: Throwable => this.answerWithError(ex)
            }
        } recoverTotal {
            case error =>
                Future.successful(this.answerWithError("Could not read submitted form", error))
        }
    }
}