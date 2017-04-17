package com.github.pjozsef.control.udp

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class UdpVerticle : AbstractVerticle(){
    override fun start(startFuture: Future<Void>) {
        startFuture.complete()
    }
}