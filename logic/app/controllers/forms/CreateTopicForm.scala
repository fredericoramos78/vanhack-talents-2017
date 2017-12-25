package controllers.forms

import play.api.libs.json.Json

case class CreateTopicForm(userId: Long, title: String)

object CreateTopicForm {
    implicit val JSON_READER = Json.reads[CreateTopicForm]
}
