package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS
import com.github.pjozsef.control.command.platform.osx.OsxVolumeStatus
import io.vertx.core.logging.LoggerFactory

class OsxCommander(os: OS) : BaseCommander(os) {

    val log = LoggerFactory.getLogger(this::class.java)

    override val supported: List<String>
        get() = listOf(
                "shutdown",
                "restart",
                "suspend",
                "playpause",
                "next",
                "prev",
                "mute",
                "volup",
                "voldown",
                "setvolume")

    override fun shutDown() {
        exec(arrayOf("osascript", "-e", "tell app \"System Events\" to shut down"), log)
    }

    override fun restart() {
        exec(arrayOf("osascript", "-e", "tell app \"System Events\" to restart"), log)
    }

    override fun suspend() {
        exec(arrayOf("osascript", "-e", "tell app \"System Events\" to sleep"), log)
    }

    override fun playPause() {
        exec(arrayOf("osascript", "-e", "tell app \"Spotify\" to playpause"), log)
    }

    override fun next() {
        exec(arrayOf("osascript", "-e", "tell app \"Spotify\" to next track"), log)
    }

    override fun prev() {
        exec(arrayOf("osascript", "-e", "tell app \"Spotify\" to previous track"), log)
    }

    override fun mute() {
        val (out, _) = exec(arrayOf("osascript", "-e", "set curVolume to get volume settings"), log)
        OsxVolumeStatus.of(out)?.let { (_, _, _, muted) ->
            if (!muted) {
                exec(arrayOf("osascript", "-e", "set volume with output muted"), log)
            } else {
                exec(arrayOf("osascript", "-e", "set volume without output muted"), log)
            }
        } ?: throw IllegalStateException("Unable to parse output: $out")
    }

    override fun volUp() {
        exec(arrayOf(
                "osascript",
                "-e",
                "set volume output volume (output volume of (get volume settings) + 10)"), log)
    }

    override fun volDown() {
        exec(arrayOf(
                "osascript",
                "-e",
                "set volume output volume (output volume of (get volume settings) - 10)"), log)
    }

    override fun setVolume(level: Int) {
        exec(arrayOf("osascript", "-e", "set volume output volume ${normalize(level)}"), log)
    }
}