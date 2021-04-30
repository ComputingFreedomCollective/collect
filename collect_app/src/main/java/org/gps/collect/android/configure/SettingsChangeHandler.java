package org.gps.collect.android.configure;

public interface SettingsChangeHandler {
    void onSettingChanged(String changedKey, Object newValue);
}
