package org.gps.collect.android.database;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.gps.collect.android.forms.FormsRepository;
import org.gps.collect.android.forms.FormsRepositoryTest;
import org.gps.collect.android.injection.config.AppDependencyModule;
import org.gps.collect.android.storage.StorageInitializer;
import org.gps.collect.android.storage.StoragePathProvider;
import org.gps.collect.android.storage.StorageSubdirectory;
import org.gps.collect.android.support.RobolectricHelpers;
import org.gps.collect.utilities.Clock;

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
