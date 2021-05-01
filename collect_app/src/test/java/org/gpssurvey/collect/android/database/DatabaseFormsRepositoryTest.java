package org.gpssurvey.collect.android.database;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.gpssurvey.collect.android.forms.FormsRepository;
import org.gpssurvey.collect.android.forms.FormsRepositoryTest;
import org.gpssurvey.collect.android.injection.config.AppDependencyModule;
import org.gpssurvey.collect.android.storage.StorageInitializer;
import org.gpssurvey.collect.android.storage.StoragePathProvider;
import org.gpssurvey.collect.android.storage.StorageSubdirectory;
import org.gpssurvey.collect.android.support.RobolectricHelpers;
import org.gpssurvey.collect.utilities.Clock;

@RunWith(AndroidJUnit4.class)
public class DatabaseFormsRepositoryTest extends FormsRepositoryTest {

    private StoragePathProvider storagePathProvider;

    @Before
    public void setup() {
        RobolectricHelpers.mountExternalStorage();
        storagePathProvider = new StoragePathProvider();
        new StorageInitializer().createOdkDirsOnStorage();
    }

    @Override
    public FormsRepository buildSubject() {
        return new DatabaseFormsRepository();
    }

    @Override
    public FormsRepository buildSubject(Clock clock) {
        RobolectricHelpers.overrideAppDependencyModule(new AppDependencyModule() {
            @Override
            public Clock providesClock() {
                return clock;
            }
        });

        return buildSubject();
    }

    @Override
    public String getFormFilesPath() {
        return storagePathProvider.getDirPath(StorageSubdirectory.FORMS);
    }
}
