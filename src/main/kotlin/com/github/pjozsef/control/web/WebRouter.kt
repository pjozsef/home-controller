package com.github.pjozsef.control.web

import com.github.pjozsef.control.model.EventBusAddress
import com.github.pjozsef.control.web.handler.AuthenticationHandler
import com.github.pjozsef.control.web.handler.CommandHandler
import com.github.pjozsef.control.web.handler.HealthCheckHandler
import com.github.pjozsef.control.web.handler.SupportedCommandsHandler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerHandler

class WebRouter {
    val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        fun create(vertx: Vertx, config: JsonObject): Router = WebRouter().create(vertx, config)
    }

    fun create(vertx: Vertx, config: JsonObject) = Router.router(vertx).apply {
        route("/*").handler(LoggerHandler.create())

        get("/healthcheck").handler(HealthCheckHandler())

        route("/command/*").handler(AuthenticationHandler(config.getString("token")))
        post("/command/*").handler(BodyHandler.create())
        get("/command/supported").handler(SupportedCommandsHandler(vertx))
        post("/command/shutdown").handler(CommandHandler(vertx, EventBusAddress.shutdown))
        post("/command/restart").handler(CommandHandler(vertx, EventBusAddress.restart))
        post("/command/suspend").handler(CommandHandler(vertx, EventBusAddress.suspend))
        post("/command/playpause").handler(CommandHandler(vertx, EventBusAddress.playPause))
        post("/command/next").handler(CommandHandler(vertx, EventBusAddress.next))
        post("/command/prev").handler(CommandHandler(vertx, EventBusAddress.prev))
        post("/command/mute").handler(CommandHandler(vertx, EventBusAddress.mute))
        post("/command/volup").handler(CommandHandler(vertx, EventBusAddress.volUp))
        post("/command/voldown").handler(CommandHandler(vertx, EventBusAddress.volDown))
        post("/command/volume").handler(CommandHandler(vertx, EventBusAddress.setVolume))
    }
}
