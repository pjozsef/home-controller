package com.github.pjozsef.control

import com.github.pjozsef.control.command.CommanderVerticle
import com.github.pjozsef.control.udp.UdpVerticle
import com.github.pjozsef.control.web.WebVerticle
import io.vertx.core.*
import io.vertx.core.logging.LoggerFactory

class DeployVerticle : AbstractVerticle() {

    val log = LoggerFactory.getLogger(this::class.java)

    override fun start(startFuture: Future<Void>) {
        val udpFuture = Future.future<Void>()
        val webFuture = Future.future<Void>()
        val commandFuture = Future.future<Void>()

        val udpOptions = DeploymentOptions().setConfig(config().getJsonObject("udp"))
        val webOptions = DeploymentOptions().setConfig(config().getJsonObject("web"))
        log.info("Using configuration: ${config()}")
        vertx.deployVerticle(UdpVerticle(), udpOptions) { handleDeployment(it, "udp", udpFuture) }

        vertx.deployVerticle(WebVerticle(), webOptions) { handleDeployment(it, "web", webFuture) }

        val commanderOptions = DeploymentOptions().setWorker(true)
        vertx.deployVerticle(CommanderVerticle(), commanderOptions) { handleDeployment(it, "commander", commandFuture) }

        CompositeFuture.all(listOf(udpFuture, webFuture, commandFuture)).setHandler { result ->
            if (result.succeeded()) {
                startFuture.complete()
            } else {
                startFuture.fail(result.cause())
            }
        }
    }

    private fun handleDeployment(result: AsyncResult<String>, name: String, future: Future<Void>) {
        if (result.succeeded()) {
            log.info("Verticle deployed ${name}Verticle with ID: ${result.result()}")
            future.complete()
        } else {
            future.fail(result.cause())
        }
    }
}