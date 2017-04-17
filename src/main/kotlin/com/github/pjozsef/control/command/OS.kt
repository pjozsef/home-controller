package com.github.pjozsef.control.command

enum class OS {
    WINDOWS, LINUX, OSX, OTHER;

    fun isSupported() = this != OTHER

    override fun toString() = this.name.capitalize()

    companion object {
        fun of(osName: String) =
                if (osName.contains("Windows")) WINDOWS
                else if (osName.contains("Linux")) LINUX
                else if (osName.contains("Mac")) OSX
                else OTHER
    }

}