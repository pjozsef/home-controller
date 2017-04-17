package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS

interface Commander {
    val platform: OS

    fun shutDown()

    fun restart()

    fun suspend()

    fun playPause()

    fun next()

    fun prev()

    fun mute()

    fun volUp()

    fun volDown()

    fun setVolume(level: Int)
}