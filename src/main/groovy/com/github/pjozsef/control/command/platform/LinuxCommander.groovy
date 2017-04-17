package com.github.pjozsef.control.command.platform

import com.github.pjozsef.control.command.OS
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class LinuxCommander extends BaseCommander {

    Logger log = LoggerFactory.getLogger(this.getClass())

    public LinuxCommander(OS os) {
        super(os)
    }

    @Override
    void shutDown() {
        exec "systemctl poweroff"
    }

    @Override
    void restart() {
        exec "systemctl reboot"
    }

    @Override
    void suspend() {
        exec "systemctl suspend"
    }

    @Override
    void playPause() {
        exec "xdotool key XF86AudioPlay"
    }

    @Override
    void next() {
        exec "xdotool key XF86AudioNext"
    }

    @Override
    void prev() {
        exec "xdotool key XF86AudioPrev"
    }

    @Override
    void mute() {
        exec "xdotool key XF86AudioMute"
    }

    @Override
    void volUp() {
        exec "xdotool key XF86AudioRaiseVolume"
    }

    @Override
    void volDown() {
        exec "xdotool key XF86AudioLowerVolume"
    }

    @Override
    void setVolume(int level) {
        level = normalize level
        exec "amixer -c 0 sset Master,0 $level%"
    }

    private void exec(String command) {
        Process process = command.execute()
        StringBuilder out = new StringBuilder()
        StringBuilder err = new StringBuilder()

        process.consumeProcessOutput(out, err)
        if (out.size() > 0) log.info("$command finished successfully:\n$out.toString()")
        if (err.size() > 0) log.error("$command error:\n$err.toString()")
    }

    private int normalize(int level) {
        if (level < 0) return 0
        else if (level > 100) return 100
        else return level
    }
}
