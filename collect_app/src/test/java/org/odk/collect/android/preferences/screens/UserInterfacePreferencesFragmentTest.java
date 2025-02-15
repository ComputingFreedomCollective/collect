package org.odk.collect.android.preferences.screens;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odk.collect.android.preferences.keys.AdminKeys;
import org.odk.collect.android.preferences.keys.GeneralKeys;
import org.odk.collect.android.preferences.source.Settings;
import org.odk.collect.utilities.TestSettingsProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.odk.collect.android.preferences.screens.GeneralPreferencesActivity.INTENT_KEY_ADMIN_MODE;

@RunWith(AndroidJUnit4.class)
public class UserInterfacePreferencesFragmentTest {
    private final Settings adminSettings = TestSettingsProvider.getAdminSettings();

    @Before
    public void setup() {
        adminSettings.clear();
        adminSettings.setDefaultForAllSettingsWithoutValues();
    }

    @Test
    public void visiblePreferences_shouldBeVisibleIfOpenedFromGeneralPreferences() {
        FragmentScenario<UserInterfacePreferencesFragment> scenario = FragmentScenario.launch(UserInterfacePreferencesFragment.class);
        scenario.onFragment(fragment -> {
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_THEME).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_LANGUAGE).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_FONT_SIZE).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_NAVIGATION).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_SHOW_SPLASH).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_SPLASH_PATH).isVisible(), equalTo(true));
        });
    }

    @Test
    public void visiblePreferences_shouldBeVisibleIfOpenedFromAdminPreferences() {
        Bundle args = new Bundle();
        args.putBoolean(INTENT_KEY_ADMIN_MODE, true);

        FragmentScenario<UserInterfacePreferencesFragment> scenario = FragmentScenario.launch(UserInterfacePreferencesFragment.class, args);
        scenario.onFragment(fragment -> {
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_THEME).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_LANGUAGE).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_FONT_SIZE).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_NAVIGATION).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_SHOW_SPLASH).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_SPLASH_PATH).isVisible(), equalTo(true));
        });
    }

    @Test
    public void hiddenPreferences_shouldBeHiddenIfOpenedFromGeneralPreferences() {
        adminSettings.save(AdminKeys.KEY_APP_THEME, false);
        adminSettings.save(AdminKeys.KEY_APP_LANGUAGE, false);
        adminSettings.save(AdminKeys.KEY_CHANGE_FONT_SIZE, false);
        adminSettings.save(AdminKeys.KEY_NAVIGATION, false);
        adminSettings.save(AdminKeys.KEY_SHOW_SPLASH_SCREEN, false);

        FragmentScenario<UserInterfacePreferencesFragment> scenario = FragmentScenario.launch(UserInterfacePreferencesFragment.class);
        scenario.onFragment(fragment -> {
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_THEME), nullValue());
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_LANGUAGE), nullValue());
            assertThat(fragment.findPreference(GeneralKeys.KEY_FONT_SIZE), nullValue());
            assertThat(fragment.findPreference(GeneralKeys.KEY_NAVIGATION), nullValue());
            assertThat(fragment.findPreference(GeneralKeys.KEY_SHOW_SPLASH), nullValue());
            assertThat(fragment.findPreference(GeneralKeys.KEY_SPLASH_PATH), nullValue());
        });
    }

    @Test
    public void hiddenPreferences_shouldBeVisibleIfOpenedFromAdminSettings() {
        adminSettings.save(AdminKeys.KEY_APP_THEME, false);
        adminSettings.save(AdminKeys.KEY_APP_LANGUAGE, false);
        adminSettings.save(AdminKeys.KEY_CHANGE_FONT_SIZE, false);
        adminSettings.save(AdminKeys.KEY_NAVIGATION, false);
        adminSettings.save(AdminKeys.KEY_SHOW_SPLASH_SCREEN, false);

        Bundle args = new Bundle();
        args.putBoolean(INTENT_KEY_ADMIN_MODE, true);

        FragmentScenario<UserInterfacePreferencesFragment> scenario = FragmentScenario.launch(UserInterfacePreferencesFragment.class, args);
        scenario.onFragment(fragment -> {
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_THEME).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_APP_LANGUAGE).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_FONT_SIZE).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_NAVIGATION).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_SHOW_SPLASH).isVisible(), equalTo(true));
            assertThat(fragment.findPreference(GeneralKeys.KEY_SPLASH_PATH).isVisible(), equalTo(true));
        });
    }
}
