package com.github.pjozsef.control.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.logging.LoggerFactory

class WebVerticle : AbstractVerticle() {
    val log = LoggerFactory.getLogger(this::class.java)

    override fun start(startFuture: Future<Void>) {
        startFuture.complete()
        vertx.createHttpServer()
                .requestHandler(WebRouter.create(vertx)::accept)
                .listen(config().getInteger("port")) { result ->
                    if (result.succeeded()) {
                        startFuture.complete()
                    } else {
                        log.error(result.cause())
                        startFuture.fail(result.cause())
                    }
                }
    }
}