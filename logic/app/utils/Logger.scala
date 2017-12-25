package utils

trait Logger {
    lazy val LOGGER = play.api.Logger(this.getClass)
}
