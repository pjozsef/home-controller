package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS
import io.vertx.core.logging.Logger
import java.io.InputStream

typealias CommandOutput = Pair<String, String>


abstract class BaseCommander(override val platform: OS) : Commander {

    protected fun exec(commandArray: Array<String>, log: Logger): CommandOutput {
        log.info("Executing ${commandArray.joinToString(" ")}")
        val process = ProcessBuilder(*commandArray).start()
        val (out, err) = process.output()
        if (out.isNotBlank()) {
            log.info(out)
        }
        if (err.isNotBlank()) {
            log.error(err)
            throw Exception(err)
        }
        return out to err
    }

    private fun Process.output() = inputStream.readAll().trim() to errorStream.readAll().trim()

    private fun InputStream.readAll() = bufferedReader().use { it.readText() }

    protected fun normalize(level: Int) =
            if (level < 0) 0
            else if (level > 100) 100
            else level

}