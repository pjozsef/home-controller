package com.github.pjozsef.control.model

object EventBusAddress {
    val shutdown = "command/shutdown"
    val restart = "command/restart"
    val suspend = "command/suspend"

    val playPause = "command/playpause"
    val next = "command/next"
    val prev = "command/prev"
    val mute = "command/mute"
    val volUp = "command/volup"
    val volDown = "command/voldown"
    val setVolume = "command/setvolume"
}