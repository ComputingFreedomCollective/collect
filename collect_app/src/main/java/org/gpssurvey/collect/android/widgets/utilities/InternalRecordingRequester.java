package org.gpssurvey.collect.android.widgets.utilities;

import androidx.activity.ComponentActivity;

import org.javarosa.form.api.FormEntryPrompt;
import org.gpssurvey.collect.android.analytics.AnalyticsEvents;
import org.gpssurvey.collect.android.formentry.FormEntryViewModel;
import org.gpssurvey.collect.android.listeners.PermissionListener;
import org.gpssurvey.collect.android.permissions.PermissionsProvider;
import org.gpssurvey.collect.android.utilities.FormEntryPromptUtils;
import org.gpssurvey.collect.audiorecorder.recorder.Output;
import org.gpssurvey.collect.audiorecorder.recording.AudioRecorder;

public class InternalRecordingRequester implements RecordingRequester {

    private final ComponentActivity activity;
    private final AudioRecorder audioRecorder;
    private final PermissionsProvider permissionsProvider;
    private final FormEntryViewModel formEntryViewModel;

    public InternalRecordingRequester(ComponentActivity activity, AudioRecorder audioRecorder, PermissionsProvider permissionsProvider, FormEntryViewModel formEntryViewModel) {
        this.activity = activity;
        this.audioRecorder = audioRecorder;
        this.permissionsProvider = permissionsProvider;
        this.formEntryViewModel = formEntryViewModel;
    }

    @Override
    public void requestRecording(FormEntryPrompt prompt) {
        permissionsProvider.requestRecordAudioPermission(activity, new PermissionListener() {
            @Override
            public void granted() {
                String quality = FormEntryPromptUtils.getAttributeValue(prompt, "quality");
                if (quality != null && quality.equals("voice-only")) {
                    audioRecorder.start(prompt.getIndex(), Output.AMR);
                } else if (quality != null && quality.equals("low")) {
                    audioRecorder.start(prompt.getIndex(), Output.AAC_LOW);
                } else {
                    audioRecorder.start(prompt.getIndex(), Output.AAC);
                }
            }

            @Override
            public void denied() {

            }
        });

        formEntryViewModel.logFormEvent(AnalyticsEvents.AUDIO_RECORDING_INTERNAL);
    }
}
