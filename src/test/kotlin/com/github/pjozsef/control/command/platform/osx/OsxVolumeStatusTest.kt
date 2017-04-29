package com.github.pjozsef.control.command.platform.osx

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class OsxVolumeStatusTest {

    @Test
    fun of_zeroVolumes_muted() {
        val expected = OsxVolumeStatus(0, 0, 0, true)
        val result = OsxVolumeStatus.of("output volume:0, input volume:0, alert volume:0, output muted:true")

        assertEquals(expected, result)
    }

    @Test
    fun of_arbitraryVolumes_muted() {
        val expected = OsxVolumeStatus(9, 54, 100, true)
        val result = OsxVolumeStatus.of("output volume:9, input volume:54, alert volume:100, output muted:true")

        assertEquals(expected, result)
    }

    @Test
    fun of_arbitraryVolumes_unmuted() {
        val expected = OsxVolumeStatus(9, 54, 100, false)
        val result = OsxVolumeStatus.of("output volume:9, input volume:54, alert volume:100, output muted:false")

        assertEquals(expected, result)
    }

    @Test
    fun of_missingOutputVolume() {
        val input = "output volume:, input volume:54, alert volume:100, output muted:false"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_missingInputVolume() {
        val input = "output volume:9, input volume:, alert volume:100, output muted:false"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_missingAlertVolume() {
        val input = "output volume:9, input volume:54, alert volume:, output muted:false"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_missingMuted() {
        val input = "output volume:9, input volume:54, alert volume:100, output muted:"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_invalidOutputVolume() {
        val input = "output volume:as83df, input volume:54, alert volume:100, output muted:false"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_invalidInputVolume() {
        val input = "output volume:9, input volume:as93df, alert volume:100, output muted:false"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_invalidAlertVolume() {
        val input = "output volume:9, input volume:54, alert volume:aad93e, output muted:false"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_invalidMuted() {
        val input = "output volume:9, input volume:54, alert volume:100, output muted:38aef"
        assertNull(OsxVolumeStatus.of(input))
    }

    @Test
    fun of_badInput() {
        val input = "yolo"
        assertNull(OsxVolumeStatus.of(input))
    }
}