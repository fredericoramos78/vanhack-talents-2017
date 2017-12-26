package services.impl

import java.sql.Connection
import java.time.LocalDateTime
import javax.inject.Inject

import model.{Comment, Topic, User}
import repo.{CommentRepository, TopicRepository, UserRepository}
import services.TopicService
import utils.{ConnectionProvider, ExecutionContexts, Logger}

import scala.concurrent.Future


class TopicServiceImpl @Inject()(topicRepo: TopicRepository, commentRepo: CommentRepository,
                                 userRepo: UserRepository, cp: ConnectionProvider[Connection], ec: ExecutionContexts)
    extends TopicService with Logger {

    implicit val threadPool = ec.SERVICES_THREADPOOL


    override def createTopic(t: Topic) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[TopicService.createTopic] Creating a new topic for user ${t.owner.toString()}")
            topicRepo.insert(t).map {
                t.inserted(_)
            }
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def searchTopic(topicId: Long) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[TopicService.searchTopic] Searching topic with id #${topicId}")
            topicRepo.select(topicId)
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def searchTopics(keywords: Seq[String]) = {
        LOGGER.debug(s"[TopicService.searchTopics] Searching topics using the following keywords: (${keywords})")
        topicRepo.searchByKeyword(keywords)
    }

    override def listHotTopics() = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[TopicService.listHotTopics] Loading hot topics")
            topicRepo.selectSortedBy(Topic.SORTBY_COMMENT_COUNT, None, 5)
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def listLatestTopics() = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[TopicService.listHotTopics] Loading topics with the most recent comments")
            topicRepo.selectSortedBy(Topic.SORTBY_LATEST_COMMENT, None, 5)
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def listMyLatestTopics(user: User) = Future {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[TopicService.listMyLatestTopics] Loading topics with the most recent comments for ${user}")
            user.id.map { userId =>
                userRepo.select(userId) match {
                    case Some(u) =>
                        topicRepo.selectSortedBy(Topic.SORTBY_LATEST_COMMENT, Some(u), 5)
                    case None =>
                        LOGGER.error(s"[TopicService.listMyLatestTopics] User ${user} not found")
                        throw new IllegalArgumentException("Cannot list latest topics for undefined user")
                }
            } getOrElse {
                LOGGER.error(s"[TopicService.listMyLatestTopics] User ${user} must have its ID specified for this operation")
                throw new IllegalArgumentException("Cannot list latest topics for undefined user")
            }
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def appendComment(topicId: Long, comment: Comment) = {
        implicit var c: Connection = null
        try {
            c = cp.openTransaction()
            LOGGER.debug(s"[TopicService.appendComment] Appending comment to topic #${topicId}")
            topicRepo.select(topicId).map { t =>
                commentRepo.insert(comment, topicId) map { cmm =>
                    if (cmm.isDefined && this.updateTopicCounters(topicId, comment.createdAt)) {
                        cmm
                    } else {
                        None
                    }
                }
            } getOrElse {
                Future.failed(throw new IllegalArgumentException(s"Topic #${topicId} does not exist"))
            }
        } finally {
            cp.commitTransaction(c)
        }
    }

    override def listComments(topicId: Long, offset: Int, limit: Int) = {
        LOGGER.debug(s"[TopicService.listComments] Listing comments for topic #${topicId}, starting at offset=${offset} up to limit=${limit}")
        commentRepo.selectByTopic(topicId, offset, limit)
    }

    private[this] def updateTopicCounters(topicId: Long, commentDatetime: LocalDateTime)(implicit c: Connection) = {
        LOGGER.debug(s"[TopicService.updateTopicCounters] Updating counters for topic #${topicId}")
        topicRepo.updateCommentCounter(topicId, commentDatetime)
    }
}