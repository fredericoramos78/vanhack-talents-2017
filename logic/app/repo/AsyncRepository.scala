package repo

import scala.concurrent.ExecutionContext

trait AsyncRepository {
    implicit val threadPool: ExecutionContext
}
