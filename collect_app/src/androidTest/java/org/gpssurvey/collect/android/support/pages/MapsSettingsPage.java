package org.gpssurvey.collect.android.support.pages;

import androidx.test.rule.ActivityTestRule;

import org.gpssurvey.collect.android.R;

public class MapsSettingsPage extends Page<MapsSettingsPage> {

    MapsSettingsPage(ActivityTestRule rule) {
        super(rule);
    }

    @Override
    public MapsSettingsPage assertOnPage() {
        assertText(R.string.maps);
        return this;
    }
}
