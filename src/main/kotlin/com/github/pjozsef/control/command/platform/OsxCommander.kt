package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS
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
        exec("osascript -e 'tell app \"System Events\" to shut down'", log)
    }

    override fun restart() {
        exec("osascript -e 'tell app \"System Events\" to restart'", log)
    }

    override fun suspend() {
        exec("osascript -e 'tell app \"System Events\" to sleep'", log)
    }

    override fun playPause() {
        exec("osascript -e 'tell application \"Spotify\" to playpause'", log)
    }

    override fun next() {
        exec("osascript -e 'tell application \"Spotify\" to next track'", log)
    }

    override fun prev() {
        exec("osascript -e 'tell application \"Spotify\" to previous track'", log)
    }

    override fun mute() {
        exec("osascript " +
                "-e 'set curVolume to get volume settings' " +
                "-e 'if output muted of curVolume is false then' " +
                "-e 'set volume with output muted' " +
                "-e 'else' " +
                "-e 'set volume without output muted' " +
                "-e 'end if'", log)
    }

    override fun volUp() {
        exec("osascript -e 'set volume output volume (output volume of (get volume settings) + 10)'", log)
    }

    override fun volDown() {
        exec("osascript -e 'set volume output volume (output volume of (get volume settings) - 10)'", log)
    }

    override fun setVolume(level: Int) {
        exec("osascript -e 'set volume output volume 100'", log)
    }
}