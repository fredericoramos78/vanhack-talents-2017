package controllers

import play.api.libs.json.{JsError, Json}
import play.api.mvc.ControllerHelpers

trait AnyController extends ControllerHelpers {

    def answerWithError(cause: Throwable) = this._answerWithError(None, Some(cause), None)
    def answerWithError(errorMessage: String) = this._answerWithError(Some(errorMessage), None, None)
    def answerWithError(errorMessage: String, jsError: JsError) = this._answerWithError(Some(errorMessage), None, Some(jsError))

    private[this] def _answerWithError(errorMessage: Option[String], cause: Option[Throwable], jsError: Option[JsError]) =
        BadRequest( Json.obj(
            "error" -> errorMessage,
            "cause" -> cause.map(_.getLocalizedMessage),
            "formError" -> jsError.map(_.toString) )
        )
}
