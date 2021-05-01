package org.gpssurvey.collect.android.utilities;

import androidx.annotation.Nullable;

public interface DeviceDetailsProvider {

    @Nullable
    String getDeviceId() throws SecurityException;

    @Nullable
    String getLine1Number() throws SecurityException;
}
