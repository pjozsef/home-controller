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
        exec("systemctl poweroff", log)
    }

    override fun restart() {
        exec("systemctl reboot", log)
    }

    override fun suspend() {
        exec("systemctl suspend", log)
    }

    override fun playPause() {
        exec("xdotool key XF86AudioPlay", log)
    }

    override fun next() {
        exec("xdotool key XF86AudioNext", log)
    }

    override fun prev() {
        exec("xdotool key XF86AudioPrev", log)
    }

    override fun mute() {
        exec("xdotool key XF86AudioMute", log)
    }

    override fun volUp() {
        exec("xdotool key XF86AudioRaiseVolume", log)
    }

    override fun volDown() {
        exec("xdotool key XF86AudioLowerVolume", log)
    }

    override fun setVolume(level: Int) {
        exec("amixer -c 0 sset Master,0 ${normalize(level)}%", log)
    }
}