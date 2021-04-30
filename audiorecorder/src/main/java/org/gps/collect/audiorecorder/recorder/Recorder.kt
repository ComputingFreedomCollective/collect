package org.gps.collect.audiorecorder.recorder

import org.gps.collect.audiorecorder.recording.MicInUseException
import org.gps.collect.audiorecorder.recording.SetupException
import java.io.File

internal interface Recorder {

    @Throws(SetupException::class, MicInUseException::class)
    fun start(output: Output)
    fun pause()
    fun resume()
    fun stop(): File
    fun cancel()

    val amplitude: Int
    fun isRecording(): Boolean
}

enum class Output {
    AMR,
    AAC,
    AAC_LOW
}
