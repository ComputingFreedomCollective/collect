package org.gpssurvey.collect.android.formentry.media;

import android.content.Context;

import org.gpssurvey.collect.android.audio.AudioHelper;

public interface AudioHelperFactory {

    AudioHelper create(Context context);
}
