package org.odk.collect.android.application.initialization.migration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.odk.collect.android.preferences.source.Settings;
import org.odk.collect.utilities.TestSettingsProvider;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.odk.collect.android.application.initialization.migration.MigrationUtils.moveKey;
import static org.odk.collect.android.application.initialization.migration.SharedPreferenceUtils.assertPrefs;
import static org.odk.collect.android.application.initialization.migration.SharedPreferenceUtils.initPrefs;

@RunWith(RobolectricTestRunner.class)
public class KeyMoverTest {

    private final Settings prefs = TestSettingsProvider.getTestSettings("test");
    private final Settings other = TestSettingsProvider.getTestSettings("other");

    @Test
    public void movesKeyAndValueToOtherPrefs() {
        initPrefs(prefs,
                "key", "value"
        );

        moveKey("key")
                .toPreferences(other)
                .apply(prefs);

        assertThat(prefs.getAll().size(), is(0));
        assertPrefs(other,
                "key", "value"
        );
    }

    @Test
    public void whenKeyNotInOriginalPrefs_doesNothing() {
        moveKey("key")
                .toPreferences(other)
                .apply(prefs);

        assertThat(prefs.getAll().size(), is(0));
        assertThat(other.getAll().size(), is(0));
    }

    @Test
    public void whenKeyInOtherPrefs_doesNothing() {
        initPrefs(prefs,
                "key", "value"
        );

        initPrefs(other,
                "key", "other-value"
        );

        moveKey("key")
                .toPreferences(other)
                .apply(prefs);

        assertPrefs(prefs,
                "key", "value"
        );

        assertPrefs(other,
                "key", "other-value"
        );
    }
}