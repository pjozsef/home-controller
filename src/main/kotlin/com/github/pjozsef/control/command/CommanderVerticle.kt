package com.github.pjozsef.control.command

import com.github.pjozsef.control.command.platform.Commander
import com.github.pjozsef.control.command.platform.LinuxCommander
import com.github.pjozsef.control.command.platform.OsxCommander
import com.github.pjozsef.control.command.platform.WindowsCommander
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory

class CommanderVerticle : AbstractVerticle() {
    lateinit var osName: String
    lateinit var os: OS
    lateinit var eb: EventBus

    val log = LoggerFactory.getLogger(this::class.java)

    val commander: Commander by lazy<Commander> {
        val os = OS.of(osName)
        when (os) {
            OS.WINDOWS -> WindowsCommander(os)
            OS.LINUX -> LinuxCommander(os)
            OS.OSX -> OsxCommander(os)
            OS.OTHER -> throw unsupportedOS()
        }
    }

    override fun start(startFuture: Future<Void>) {
        initialize()

        if (os.isSupported()) {
            log.info("Running on $osName")
            subscribeToEventBus()
            startFuture.complete()
        } else {
            startFuture.fail(unsupportedOS())
        }
    }

    private fun initialize() {
        osName = System.getProperty("os.name")
        os = OS.of(osName)
        eb = vertx.eventBus()
    }

    private fun subscribeToEventBus() {
        eb.consumer<JsonObject>("command/shutdown", handleShutdown)
        eb.consumer<JsonObject>("command/restart", handleRestart)
        eb.consumer<JsonObject>("command/suspend", handleSuspend)

        eb.consumer<JsonObject>("command/playpause", handlePlayPause)
        eb.consumer<JsonObject>("command/next", handleNext)
        eb.consumer<JsonObject>("command/prev", handlePrev)
        eb.consumer<JsonObject>("command/volup", handleVolUp)
        eb.consumer<JsonObject>("command/voldown", handleVolDown)
    }

    private val handleShutdown: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleRestart: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleSuspend: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handlePlayPause: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleNext: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handlePrev: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleVolUp: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleVolDown: (Message<JsonObject>) -> Unit = { message ->

    }

    private fun unsupportedOS() = IllegalStateException("Unsupported OS version: $osName")
}