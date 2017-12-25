package services.impl

import java.sql.Connection
import java.time.LocalDateTime
import javax.inject.Inject

import model.{Comment, Topic, User}
import play.api.db.Database
import repo.{CommentRepository, TopicRepository, UserRepository}
import services.TopicService
import utils.{ExecutionContexts, Logger}

import scala.concurrent.Future



class TopicServiceImpl @Inject()(topicRepo: TopicRepository, commentRepo: CommentRepository,
                                 userRepo: UserRepository, db: Database, ec: ExecutionContexts)
    extends TopicService with Logger {

    implicit val threadPool = ec.SERVICES_THREADPOOL



    override def createTopic(t: Topic) = db.withTransaction { implicit c =>
        LOGGER.debug(s"[TopicService.createTopic] Creating a new topic for user ${t.owner.toString()}")
        topicRepo.insert(t).map { _.map(t.inserted(_)) }
    }

    override def searchTopics(keywords: Seq[String]) = {
        LOGGER.debug(s"[TopicService.searchTopics] Searching topics using the following keywords: (${keywords})")
        topicRepo.searchByKeyword(keywords)
    }

    override def listHotTopics() = db.withTransaction { implicit c =>
        LOGGER.debug(s"[TopicService.listHotTopics] Loading hot topics")
        topicRepo.selectSortedBy(Topic.SORTBY_COMMENT_COUNT, None, 5)
    }

    override def listLatestTopics() = db.withTransaction { implicit c =>
        LOGGER.debug(s"[TopicService.listHotTopics] Loading topics with the most recent comments")
        topicRepo.selectSortedBy(Topic.SORTBY_LATEST_COMMENT, None, 5)
    }

    override def listMyLatestTopics(user: User) = db.withTransaction { implicit c =>
        LOGGER.debug(s"[TopicService.listMyLatestTopics] Loading topics with the most recent comments for ${user}")
        user.id.map { userId =>
            userRepo.select(userId).flatMap { maybeUser =>
                maybeUser match {
                    case Some(u) =>
                        topicRepo.selectSortedBy(Topic.SORTBY_LATEST_COMMENT, Some(u), 5)
                    case None =>
                        LOGGER.error(s"[TopicService.listMyLatestTopics] User ${user} not found")
                        throw new IllegalArgumentException("Cannot list latest topics for undefined user")
                }
            }
        } getOrElse {
            LOGGER.error(s"[TopicService.listMyLatestTopics] User ${user} must have its ID specified for this operation")
            throw new IllegalArgumentException("Cannot list latest topics for undefined user")
        }
    }

    override def appendComment(topicId: Long, comment: Comment) = db.withTransaction { implicit c =>
        LOGGER.debug(s"[TopicService.appendComment] Appending comment to topic #${topicId}")
        for (// 1: topic must exist
             t <- topicRepo.select(topicId);
             // 2: comment insertion was successful
             cmm <- commentRepo.insert(comment, topicId) if t.isDefined == true;
             // 3: counters update was successful
             ok <- this.updateTopicCounters(topicId, comment.createdAt)
        ) yield { cmm }
    }

    override def listComments(topicId: Long, offset: Int, limit: Int) = {
        LOGGER.debug(s"[TopicService.listComments] Listing comments for topic #${topicId}, starting at offset=${offset} up to limit=${limit}")
        commentRepo.selectByTopic(topicId, offset, limit)
    }

    private[this] def updateTopicCounters(topicId: Long, commentDatetime: LocalDateTime)(implicit c: Connection): Future[Boolean] = {
        LOGGER.debug(s"[TopicService.updateTopicCounters] Updating counters for topic #${topicId}")
        topicRepo.updateCommentCounter(topicId, commentDatetime)
    }
}