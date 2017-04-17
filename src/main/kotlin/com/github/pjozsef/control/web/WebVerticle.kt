package com.github.pjozsef.control.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class WebVerticle: AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        startFuture.complete()
    }
}