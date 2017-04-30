package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS
import io.vertx.core.logging.LoggerFactory

class LinuxCommander(os: OS) : BaseCommander(os) {

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
        exec(arrayOf("systemctl", "poweroff"), log)
    }

    override fun restart() {
        exec(arrayOf("systemctl", "reboot"), log)
    }

    override fun suspend() {
        exec(arrayOf("systemctl", "suspend"), log)
    }

    override fun playPause() {
        exec(arrayOf("xdotool", "key", "XF86AudioPlay"), log)
    }

    override fun next() {
        exec(arrayOf("xdotool", "key", "XF86AudioNext"), log)
    }

    override fun prev() {
        exec(arrayOf("xdotool", "key", "XF86AudioPrev"), log)
    }

    override fun mute() {
        exec(arrayOf("xdotool", "key", "XF86AudioMute"), log)
    }

    override fun volUp() {
        exec(arrayOf("xdotool", "key", "XF86AudioRaiseVolume"), log)
    }

    override fun volDown() {
        exec(arrayOf("xdotool", "key", "XF86AudioLowerVolume"), log)
    }

    override fun setVolume(level: Int) {
        exec(arrayOf("amixer", "-c", "0", "sset", "Master,0", "${normalize(level)}%"), log)
    }
}
