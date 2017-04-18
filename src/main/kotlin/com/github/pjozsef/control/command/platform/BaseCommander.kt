package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS
import io.vertx.core.logging.Logger

abstract class BaseCommander(override val platform: OS) : Commander {
    private val runner = CommandRunner()

    protected fun exec(command: String, log: Logger) {
        runner.exec(command, log)
    }

    protected fun normalize(level: Int) =
            if (level < 0) 0
            else if (level > 100) 100
            else level

}