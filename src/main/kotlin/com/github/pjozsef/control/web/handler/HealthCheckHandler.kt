package com.github.pjozsef.control.web.handler

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class HealthCheckHandler : Handler<RoutingContext> {
    override fun handle(ctx: RoutingContext) {
        ctx.response().end()
    }
}