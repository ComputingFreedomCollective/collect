package org.gps.collect.android.preferences;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.gps.collect.android.R;
import org.gps.collect.android.activities.MainMenuActivity;
import org.gps.collect.android.injection.DaggerUtils;

import static org.gps.collect.android.activities.ActivityUtils.startActivityAndCloseAllOthers;

public class ExperimentalPreferencesFragment extends BasePreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.experimental_preferences, rootKey);

        findPreference(GeneralKeys.KEY_MAGENTA_THEME).setOnPreferenceChangeListener((preference, newValue) -> {
            startActivityAndCloseAllOthers(requireActivity(), MainMenuActivity.class);
            return true;
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        DaggerUtils.getComponent(context).inject(this);
    }
}
