package controllers

import play.api.libs.json.{JsError, Json}
import play.api.mvc.ControllerHelpers
import utils.Logger

trait AnyController extends ControllerHelpers with Logger {

    def answerWithError(cause: Throwable) = {
        LOGGER.error("Returning error to client:", cause)
        this._answerWithError(None, Some(cause), None)
    }

    def answerWithError(errorMessage: String) = {
        LOGGER.error(s"Returning error to client: ${errorMessage}")
        this._answerWithError(Some(errorMessage), None, None)
    }
    def answerWithError(errorMessage: String, jsError: JsError) = {
        LOGGER.error(s"Returning error to client: ${errorMessage}")
        this._answerWithError(Some(errorMessage), None, Some(jsError))
    }

    private[this] def _answerWithError(errorMessage: Option[String], cause: Option[Throwable], jsError: Option[JsError]) =
        BadRequest( Json.obj(
            "error" -> errorMessage,
            "cause" -> cause.map(_.getLocalizedMessage),
            "formError" -> jsError.map(_.toString) )
        )
}
