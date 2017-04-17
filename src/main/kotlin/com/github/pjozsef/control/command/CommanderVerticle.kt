package com.github.pjozsef.control.command

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class CommanderVerticle: AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        startFuture.complete()
    }
}