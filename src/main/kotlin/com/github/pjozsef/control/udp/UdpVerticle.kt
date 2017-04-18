package com.github.pjozsef.control.udp

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.datagram.DatagramSocketOptions
import io.vertx.core.logging.LoggerFactory

class UdpVerticle : AbstractVerticle() {
    val log = LoggerFactory.getLogger(this::class.java)

    override fun start(startFuture: Future<Void>) {
        val opts = DatagramSocketOptions().setBroadcast(true)
        val socket = vertx.createDatagramSocket(opts)
        socket.listen(config().getInteger("port"), "0.0.0.0", { asyncResult ->
            if (asyncResult.succeeded()) {
                socket.handler({ packet ->
                    log.info("got: ${packet.data()}")
                })
            } else {
                println("Listen failed ${asyncResult.cause()}")
            }
        })
    }
}