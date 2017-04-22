package com.github.pjozsef.control.web

import com.github.pjozsef.control.web.handler.HealthCheckHandler
import io.vertx.core.Vertx
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerHandler

class WebRouter {
    val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        fun create(vertx: Vertx): Router = WebRouter().create(vertx)
    }

    fun create(vertx: Vertx) = Router.router(vertx).apply {
        route("/*").handler(LoggerHandler.create())
        post("/*").handler(BodyHandler.create())
        get("/healthcheck").handler(HealthCheckHandler())
    }
}