package controllers

import javax.inject._

import controllers.forms.{CreateCommentForm, CreateTopicForm, SearchTopicForm}
import model.{Comment, Topic, User}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import services.TopicService
import utils.ExecutionContexts

import scala.concurrent.Future


@Singleton
class TopicsController @Inject() (mcc: MessagesControllerComponents, conf: Configuration,
                                  ec: ExecutionContexts, topicService: TopicService)
    extends MessagesAbstractController(mcc) with AnyController {

    implicit val threadPool = ec.CONTROLLERS_THREADPOOL

    import Comment._
    import Topic._



    def putTopic() = Action.async(parse.json) { request =>
        request.body.validate[CreateTopicForm](CreateTopicForm.JSON_READER).map { form =>
            topicService.createTopic(new Topic(form.title, form.userId)).map { t =>
                Ok(Json.toJson(t))
            } recover {
                case ex: Throwable => this.answerWithError(ex)
            }
        } recoverTotal {
            case error =>
                Future.successful(this.answerWithError("Could not read submitted form", error))
        }
    }

    def getTopics() = Action.async(parse.json) { request =>
        request.body.validate[SearchTopicForm](SearchTopicForm.JSON_READER).map { form =>
            topicService.searchTopics(form.toKeywords()).map { t =>
                Ok(Json.toJson(t))
            } recover {
                case ex: Throwable => this.answerWithError(ex)
            }
        } recoverTotal {
            case error =>
                Future.successful(this.answerWithError("Could not read submitted form", error))
        }
    }

    def getHotTopics() = Action.async(parse.json) { request =>
        topicService.listHotTopics().map { t =>
            Ok(Json.toJson(t))
        } recover {
            case ex: Throwable => this.answerWithError(ex)
        }
    }

    def getLatestTopics() = Action.async(parse.json) { request =>
        topicService.listLatestTopics().map { t =>
            Ok(Json.toJson(t))
        } recover {
            case ex: Throwable => this.answerWithError(ex)
        }
    }

    def getMyLatestTopics(userId: Long) = Action.async(parse.json) { request =>
        topicService.listMyLatestTopics(new User(Some(userId), null, null)).map { t =>
            Ok(Json.toJson(t))
        } recover {
            case ex: Throwable => this.answerWithError(ex)
        }
    }

    def putComment(topicId: Long) = Action.async(parse.json) { request =>
        request.body.validate[CreateCommentForm](CreateCommentForm.JSON_READER).map { form =>
            val comment = new Comment(topicId, form.userId, form.comment)
            topicService.appendComment(topicId, comment).map { c =>
                Ok(Json.toJson(c))
            } recover {
                case ex: Throwable => this.answerWithError(ex)
            }
        } recoverTotal {
            case error =>
                Future.successful(this.answerWithError("Could not read submitted form", error))
        }
    }

    def getComments(topicId: Long, pageId: Int) = Action.async(parse.json) { request =>
        val limit: Int = 25
        val offset: Int = (pageId - 1) * limit
        topicService.listComments(topicId, offset, limit).map { c =>
            Ok(Json.toJson(c))
        } recover {
            case ex: Throwable => this.answerWithError(ex)
        }
    }
}