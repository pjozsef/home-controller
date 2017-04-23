package com.github.pjozsef.control.web.handler

import com.github.pjozsef.control.model.EventBusAddress
import com.github.pjozsef.control.util.handleAsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class SupportedCommandsHandler(val vertx: Vertx) : Handler<RoutingContext> {
    val log = LoggerFactory.getLogger(this::class.java)
    val eb = vertx.eventBus()

    override fun handle(ctx: RoutingContext) {
        eb.send<JsonArray>(EventBusAddress.supported, "") { result ->
            handleAsyncResult(result, log) { message ->
                ctx.response().end(message.body().toString())
            }
        }
    }
}