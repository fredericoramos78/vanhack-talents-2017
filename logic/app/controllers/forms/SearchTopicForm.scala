package controllers.forms

import play.api.libs.json.Json

case class SearchTopicForm(searchString: String) {

    def toKeywords(): Seq[String] = searchString.split("\\b")
}

object SearchTopicForm {
    implicit val JSON_READER = Json.reads[SearchTopicForm]
}

