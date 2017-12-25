package controllers.forms

import play.api.libs.json.Json

case class RegisterUserForm(emailAddress: String, fullName: String)

object RegisterUserForm {
    implicit val JSON_READER = Json.reads[RegisterUserForm]
}


