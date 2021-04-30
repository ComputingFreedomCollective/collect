package org.gps.collect.android.configure.legacy;

import org.gps.collect.android.R;
import org.gps.collect.android.analytics.Analytics;
import org.gps.collect.android.configure.SettingsImporter;
import org.gps.collect.android.storage.StoragePathProvider;
import org.gps.collect.android.utilities.FileUtils;
import org.gps.collect.android.utilities.ToastUtils;

import java.io.ByteArrayInputStream;
import java.io.File;

import static org.gps.collect.android.analytics.AnalyticsEvents.SETTINGS_IMPORT_JSON;
import static org.gps.collect.android.analytics.AnalyticsEvents.SETTINGS_IMPORT_SERIALIZED;

public class LegacySettingsFileImporter {

    private final StoragePathProvider storagePathProvider;
    private final Analytics analytics;
    private final SettingsImporter settingsImporter;

    public LegacySettingsFileImporter(StoragePathProvider storagePathProvider, Analytics analytics, SettingsImporter settingsImporter) {
        this.storagePathProvider = storagePathProvider;
        this.analytics = analytics;
        this.settingsImporter = settingsImporter;
    }

    public boolean importFromFile() {
        LegacySettingsFileReader legacySettingsFileReader = new LegacySettingsFileReader(storagePathProvider);

        try {
            String settings = legacySettingsFileReader.toJSON();

            if (settings != null) {
                String type = new File(storagePathProvider.getStorageRootDirPath() + "/collect.settings.json").exists()
                        ? SETTINGS_IMPORT_JSON : SETTINGS_IMPORT_SERIALIZED;
                String settingsHash = FileUtils.getMd5Hash(new ByteArrayInputStream(settings.getBytes()));

                if (settingsImporter.fromJSON(settings)) {
                    analytics.logEvent(type, "Success", settingsHash);
                    return true;
                } else {
                    ToastUtils.showLongToast(R.string.corrupt_settings_file_notification);
                    analytics.logEvent(type, "Corrupt", settingsHash);
                    return false;
                }
            } else {
                return false;
            }
        } catch (LegacySettingsFileReader.CorruptSettingsFileException e) {
            ToastUtils.showLongToast(R.string.corrupt_settings_file_notification);

            String type = new File(storagePathProvider.getStorageRootDirPath() + "/collect.settings.json").exists()
                    ? SETTINGS_IMPORT_JSON : SETTINGS_IMPORT_SERIALIZED;
            analytics.logEvent(type, "Corrupt exception", "none");
            return false;
        } finally {
            legacySettingsFileReader.delete();
        }
    }
}
