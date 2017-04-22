package com.github.pjozsef.control.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.logging.LoggerFactory

class WebVerticle : AbstractVerticle() {
    val log = LoggerFactory.getLogger(this::class.java)

    override fun start(startFuture: Future<Void>) {
        val server = vertx.createHttpServer()
        server.requestHandler(WebRouter.create(vertx)::accept)
                .listen(0) { result ->
                    if (result.succeeded()) {
                        startFuture.complete()
                        log.info("Web server listening on ${server.actualPort()}")
                    } else {
                        log.error(result.cause())
                        startFuture.fail(result.cause())
                    }
                }
    }
}