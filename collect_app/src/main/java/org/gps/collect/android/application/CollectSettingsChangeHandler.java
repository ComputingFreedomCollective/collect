package org.gps.collect.android.application;

import android.content.SharedPreferences;

import org.gps.collect.android.analytics.Analytics;
import org.gps.collect.android.analytics.AnalyticsEvents;
import org.gps.collect.android.backgroundwork.FormUpdateManager;
import org.gps.collect.android.configure.ServerRepository;
import org.gps.collect.android.configure.SettingsChangeHandler;
import org.gps.collect.android.logic.PropertyManager;
import org.gps.collect.android.preferences.PreferencesProvider;
import org.gps.collect.android.utilities.FileUtils;

import java.io.ByteArrayInputStream;

import static org.gps.collect.android.preferences.GeneralKeys.KEY_EXTERNAL_APP_RECORDING;
import static org.gps.collect.android.preferences.GeneralKeys.KEY_FORM_UPDATE_MODE;
import static org.gps.collect.android.preferences.GeneralKeys.KEY_PERIODIC_FORM_UPDATES_CHECK;
import static org.gps.collect.android.preferences.GeneralKeys.KEY_PROTOCOL;
import static org.gps.collect.android.preferences.GeneralKeys.KEY_SERVER_URL;

public class CollectSettingsChangeHandler implements SettingsChangeHandler {

    private final PropertyManager propertyManager;
    private final FormUpdateManager formUpdateManager;
    private final ServerRepository serverRepository;
    private final Analytics analytics;
    private final PreferencesProvider preferencesProvider;

    public CollectSettingsChangeHandler(PropertyManager propertyManager, FormUpdateManager formUpdateManager, ServerRepository serverRepository, Analytics analytics, PreferencesProvider preferencesProvider) {
        this.propertyManager = propertyManager;
        this.formUpdateManager = formUpdateManager;
        this.serverRepository = serverRepository;
        this.analytics = analytics;
        this.preferencesProvider = preferencesProvider;
    }

    @Override
    public void onSettingChanged(String changedKey, Object newValue) {
        propertyManager.reload();

        if (changedKey.equals(KEY_FORM_UPDATE_MODE) || changedKey.equals(KEY_PERIODIC_FORM_UPDATES_CHECK) || changedKey.equals(KEY_PROTOCOL)) {
            formUpdateManager.scheduleUpdates();
        }

        if (changedKey.equals(KEY_SERVER_URL)) {
            serverRepository.save((String) newValue);
        }

        if (changedKey.equals(KEY_EXTERNAL_APP_RECORDING) && !((Boolean) newValue)) {
            SharedPreferences generalSharedPrefs = preferencesProvider.getGeneralSharedPreferences();
            String currentServerUrl = generalSharedPrefs.getString(KEY_SERVER_URL, "");
            String serverHash = FileUtils.getMd5Hash(new ByteArrayInputStream(currentServerUrl.getBytes()));

            analytics.logServerEvent(AnalyticsEvents.INTERNAL_RECORDING_OPT_IN, serverHash);
        }
    }
}
