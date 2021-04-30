package org.gps.collect.audiorecorder.recording

import android.app.Application
import org.gps.collect.audiorecorder.getComponent
import org.gps.collect.audiorecorder.recording.internal.ForegroundServiceAudioRecorder

open class AudioRecorderFactory(private val application: Application) {

    open fun create(): AudioRecorder {
        return ForegroundServiceAudioRecorder(application, application.getComponent().recordingRepository())
    }
}
