package com.github.pjozsef.control.util

fun notNull(vararg objects: Any?) =
        objects.all { it != null }
