package services

import model.{Comment, Topic, User}

import scala.concurrent.Future


trait TopicService {

    def createTopic(t: Topic): Future[Option[Topic]]

    def searchTopic(topicId: Long): Future[Option[Topic]]

    def searchTopics(keywords: Seq[String]): Future[Seq[Topic]]

    def listHotTopics(): Future[Seq[Topic]]

    def listLatestTopics(): Future[Seq[Topic]]

    def listMyLatestTopics(user: User): Future[Seq[Topic]]

    def appendComment(topicId: Long, comment: Comment): Future[Option[Comment]]

    def listComments(topicId: Long, offset: Int, limit: Int): Future[Seq[Comment]]

}
