package com.github.pjozsef.control.web.handler

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class AuthenticationHandler(val token: String) : Handler<RoutingContext> {

    override fun handle(ctx: RoutingContext) {
        val tokenHeader = ctx.request().getHeader("token")
        if (tokenHeader != null && tokenHeader == token) {
            ctx.next()
        } else {
            ctx.response().setStatusCode(401).end()
        }
    }

}