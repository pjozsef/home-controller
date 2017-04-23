package com.github.pjozsef.control.command

import com.github.pjozsef.control.command.platform.Commander
import com.github.pjozsef.control.command.platform.LinuxCommander
import com.github.pjozsef.control.command.platform.OsxCommander
import com.github.pjozsef.control.command.platform.WindowsCommander
import com.github.pjozsef.control.model.EventBusAddress
import com.github.pjozsef.control.model.SharedDataKey
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
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
            initializeSharedData()
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

    private fun initializeSharedData() {
        val sd = vertx.sharedData()
        val map = sd.getLocalMap<String, JsonArray>(SharedDataKey.supportedInfo)
        map.put(SharedDataKey.supportedInfoList, JsonArray(commander.supported))
    }

    private fun subscribeToEventBus() {
        eb.consumer<JsonObject>(EventBusAddress.shutdown, handleShutdown)
        eb.consumer<JsonObject>(EventBusAddress.restart, handleRestart)
        eb.consumer<JsonObject>(EventBusAddress.suspend, handleSuspend)

        eb.consumer<JsonObject>(EventBusAddress.playPause, handlePlayPause)
        eb.consumer<JsonObject>(EventBusAddress.next, handleNext)
        eb.consumer<JsonObject>(EventBusAddress.prev, handlePrev)
        eb.consumer<JsonObject>(EventBusAddress.mute, handleMute)
        eb.consumer<JsonObject>(EventBusAddress.volUp, handleVolUp)
        eb.consumer<JsonObject>(EventBusAddress.volDown, handleVolDown)
        eb.consumer<JsonObject>(EventBusAddress.setVolume, handleSetVolume)
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

    private val handleMute: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleVolUp: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleVolDown: (Message<JsonObject>) -> Unit = { message ->

    }

    private val handleSetVolume: (Message<JsonObject>) -> Unit = { message ->

    }

    private fun unsupportedOS() = IllegalStateException("Unsupported OS version: $osName")
}