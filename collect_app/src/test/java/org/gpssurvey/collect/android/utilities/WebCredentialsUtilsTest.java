package org.gpssurvey.collect.android.utilities;

import org.junit.Test;
import org.gpssurvey.collect.android.logic.PropertyManager;
import org.gpssurvey.collect.android.preferences.GeneralKeys;
import org.gpssurvey.collect.android.preferences.GeneralSharedPreferences;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WebCredentialsUtilsTest {

    @Test
    public void saveCredentialsPreferencesMethod_shouldSaveNewCredentialsAndReloadPropertyManager() {
        WebCredentialsUtils webCredentialsUtils = new WebCredentialsUtils();
        GeneralSharedPreferences generalSharedPreferences = mock(GeneralSharedPreferences.class);
        PropertyManager propertyManager = mock(PropertyManager.class);

        webCredentialsUtils.saveCredentialsPreferences(generalSharedPreferences, "username", "password", propertyManager);

        verify(generalSharedPreferences, times(1)).save(GeneralKeys.KEY_USERNAME, "username");
        verify(generalSharedPreferences, times(1)).save(GeneralKeys.KEY_PASSWORD, "password");
        verify(propertyManager, times(1)).reload();
    }
}
