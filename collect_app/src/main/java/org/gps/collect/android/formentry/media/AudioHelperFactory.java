package org.gps.collect.android.formentry.media;

import android.content.Context;

import org.gps.collect.android.audio.AudioHelper;

public interface AudioHelperFactory {

    AudioHelper create(Context context);
}
