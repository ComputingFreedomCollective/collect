package org.gpssurvey.collect.android.utilities;

import org.gpssurvey.collect.android.BuildConfig;
import org.gpssurvey.collect.utilities.UserAgentProvider;

public final class AndroidUserAgent implements UserAgentProvider {

    @Override
    public String getUserAgent() {
        return String.format("%s/%s %s",
                BuildConfig.APPLICATION_ID,
                BuildConfig.VERSION_NAME,
                System.getProperty("http.agent"));
    }

}
