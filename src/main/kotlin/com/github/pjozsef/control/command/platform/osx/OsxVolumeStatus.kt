package com.github.pjozsef.control.command.platform.osx

data class OsxVolumeStatus(val output: Int, val input: Int, val alert: Int, val muted: Boolean) {
    companion object {
        private val volumeStatusRegex = Regex("output volume:(?<output>\\d+), input volume:(?<input>\\d+), alert volume:(?<alert>\\d+), output muted:(?<muted>true|false)")

        fun of(input: String): OsxVolumeStatus? {
            val match = volumeStatusRegex.matchEntire(input)
            val values = match?.groups?.drop(1)?.map { it?.value }?.toList()

            return if (values != null && values.all { it != null }) {
                val output = values[0]!!.toInt()
                val input = values[1]!!.toInt()
                val alert = values[2]!!.toInt()
                val muted = values[3]!!.toBoolean()
                OsxVolumeStatus(output, input, alert, muted)
            } else null
        }
    }
}