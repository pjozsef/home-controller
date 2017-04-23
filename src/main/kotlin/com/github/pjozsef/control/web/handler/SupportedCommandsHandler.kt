package com.github.pjozsef.control.web.handler

import com.github.pjozsef.control.model.SharedDataKey
import com.github.pjozsef.control.util.json
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class SupportedCommandsHandler(val vertx: Vertx) : Handler<RoutingContext> {
    val log = LoggerFactory.getLogger(this::class.java)
    val sd = vertx.sharedData()

    override fun handle(ctx: RoutingContext) {
        val map = sd.getLocalMap<String, JsonArray>(SharedDataKey.supportedInfo)
        val commands = map.get(SharedDataKey.supportedInfoList)
        commands?.let {
            val response = json { "commands" to commands }
            ctx.response().end(response.toString())
        }
    }
}