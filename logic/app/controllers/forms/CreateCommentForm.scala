package controllers.forms

import play.api.libs.json.Json

case class CreateCommentForm(userId: Long, comment: String)

object CreateCommentForm {
    implicit val JSON_READER = Json.reads[CreateCommentForm]
}

