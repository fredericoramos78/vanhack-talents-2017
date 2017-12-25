package repo

import scala.concurrent.Future

trait KeywordBasedRepository[T] {
    self: AsyncRepository =>

    def searchByKeyword(keywords: Seq[String]): Future[Seq[T]]
}
