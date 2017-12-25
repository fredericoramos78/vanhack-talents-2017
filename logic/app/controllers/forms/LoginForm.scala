package controllers.forms

import play.api.libs.json.Json

case class LoginForm(emailAddress: String, password: String)

object LoginForm {
    implicit val JSON_READER = Json.reads[LoginForm]
}
