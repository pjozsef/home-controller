package com.github.pjozsef.control.command.platform

class CommandRunner {
    def exec(String command, log) {
        Process process = command.execute()
        StringBuilder out = new StringBuilder()
        StringBuilder err = new StringBuilder()

        process.consumeProcessOutput(out, err)
        if (out.size() > 0) log.info("$command finished successfully:\n$out.toString()")
        if (err.size() > 0) log.error("$command error:\n$err.toString()")
    }
}
