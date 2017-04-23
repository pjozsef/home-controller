package com.github.pjozsef.control.web

import com.github.pjozsef.control.model.SharedDataKey
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServer
import io.vertx.core.logging.LoggerFactory
import java.net.InetAddress

class WebVerticle : AbstractVerticle() {
    val log = LoggerFactory.getLogger(this::class.java)

    override fun start(startFuture: Future<Void>) {
        val server = vertx.createHttpServer()
        server.requestHandler(WebRouter.create(vertx, config())::accept)
                .listen(0) { result ->
                    if (result.succeeded()) {
                        server.initSharedData()
                        log.info("Web server listening on ${server.actualPort()}")
                        startFuture.complete()
                    } else {
                        log.error(result.cause())
                        startFuture.fail(result.cause())
                    }
                }
    }

    private fun HttpServer.initSharedData() {
        val sd = vertx.sharedData()
        val webInfo = sd.getLocalMap<String, String>(SharedDataKey.webInfo)
        webInfo.put(SharedDataKey.webInfoName, InetAddress.getLocalHost().hostName)
        webInfo.put(SharedDataKey.webInfoAddress, InetAddress.getLocalHost().hostAddress)
        webInfo.put(SharedDataKey.webInfoPort, this.actualPort().toString())
        webInfo.put(SharedDataKey.webInfoOS, System.getProperty("os.name"))
        log.info("WebInfo stored in sharedData")
    }
}