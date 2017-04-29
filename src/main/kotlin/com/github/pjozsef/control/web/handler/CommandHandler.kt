package com.github.pjozsef.control.web.handler

import com.github.pjozsef.control.util.json
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class CommandHandler(val vertx: Vertx, val address: String) : Handler<RoutingContext> {
    val log = LoggerFactory.getLogger(this::class.java)
    val eb = vertx.eventBus()

    override fun handle(ctx: RoutingContext) {
        val body = if (ctx.body.length() != 0) ctx.bodyAsJson else json {}
        eb.send<JsonObject>(address, body) { result ->
            if (result.succeeded()) {
                ctx.response().end()
            } else {
                val errorJson = json { "error" to result.cause().message }
                ctx.response()
                        .setStatusCode(500)
                        .end(errorJson.encode())
            }
        }
    }
}
