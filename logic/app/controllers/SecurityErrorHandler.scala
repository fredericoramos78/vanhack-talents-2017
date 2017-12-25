package controllers

import javax.inject._

import play.api.http.DefaultHttpErrorHandler
import play.api.routing.Router
import play.api.{Configuration, OptionalSourceMapper}


class SecurityErrorHandler @Inject() (env: play.api.Environment, config: Configuration,
                                      sourceMapper: OptionalSourceMapper, router: javax.inject.Provider[Router])
        extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

}
