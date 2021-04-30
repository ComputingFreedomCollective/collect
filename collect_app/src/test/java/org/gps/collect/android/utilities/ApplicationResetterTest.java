package org.gps.collect.android.utilities;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.gps.collect.android.configure.ServerRepository;
import org.gps.collect.android.injection.config.AppDependencyModule;
import org.gps.collect.android.preferences.PreferencesProvider;
import org.gps.collect.android.support.RobolectricHelpers;
import org.robolectric.RobolectricTestRunner;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class ApplicationResetterTest {

    private final ServerRepository serverRepository = mock(ServerRepository.class);

    @Before
    public void setup() {
        RobolectricHelpers.overrideAppDependencyModule(new AppDependencyModule() {
            @Override
            public ServerRepository providesServerRepository(Context context, PreferencesProvider preferencesProvider) {
                return serverRepository;
            }
        });
    }

    @Test
    public void resettingPreferences_alsoResetsServers() {
        ApplicationResetter applicationResetter = new ApplicationResetter();
        applicationResetter.reset(asList(ApplicationResetter.ResetAction.RESET_PREFERENCES));
        verify(serverRepository).clear();
    }
}