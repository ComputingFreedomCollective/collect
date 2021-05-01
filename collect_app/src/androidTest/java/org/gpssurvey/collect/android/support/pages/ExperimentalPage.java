package org.gpssurvey.collect.android.support.pages;

import androidx.test.rule.ActivityTestRule;

import org.gpssurvey.collect.android.R;

public class ExperimentalPage extends Page<ExperimentalPage> {

    ExperimentalPage(ActivityTestRule rule) {
        super(rule);
    }

    @Override
    public ExperimentalPage assertOnPage() {
        assertToolbarTitle(getTranslatedString(R.string.experimental));
        return this;
    }
}
