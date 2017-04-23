package com.github.pjozsef.control.web.handler

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class AuthenticationHandler(val token: String) : Handler<RoutingContext> {

    override fun handle(ctx: RoutingContext) {
        val tokenParam = ctx.request().getParam("token")
        if (tokenParam != null && tokenParam == token) {
            ctx.next()
        } else {
            ctx.response().setStatusCode(401).end()
        }
    }

}