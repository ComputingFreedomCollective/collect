package org.odk.collect.android.configure.qr;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odk.collect.android.R;

import org.odk.collect.android.preferences.source.Settings;
import org.odk.collect.testshared.FakeScheduler;
import org.odk.collect.utilities.TestSettingsProvider;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.odk.collect.android.preferences.keys.AdminKeys.KEY_ADMIN_PW;
import static org.odk.collect.android.preferences.keys.GeneralKeys.KEY_PASSWORD;

@RunWith(AndroidJUnit4.class)
public class QRCodeViewModelTest {

    private final QRCodeGenerator qrCodeGenerator = mock(QRCodeGenerator.class);
    private final JsonPreferencesGenerator jsonPreferencesGenerator = mock(JsonPreferencesGenerator.class);
    private final FakeScheduler fakeScheduler = new FakeScheduler();

    private final Settings generalSettings = TestSettingsProvider.getGeneralSettings();
    private final Settings adminSettings = TestSettingsProvider.getAdminSettings();

    @Test
    public void setIncludedKeys_generatesQRCodeWithKeys() throws Exception {
        QRCodeViewModel viewModel = new QRCodeViewModel(qrCodeGenerator, jsonPreferencesGenerator, generalSettings, adminSettings, fakeScheduler);

        viewModel.setIncludedKeys(asList("foo", "bar"));
        fakeScheduler.runBackground();

        verify(qrCodeGenerator).generateQRCode(asList("foo", "bar"), jsonPreferencesGenerator);
    }

    @Test
    public void warning_whenNeitherServerOrAdminPasswordSet_isNull() {
        QRCodeViewModel viewModel = new QRCodeViewModel(qrCodeGenerator, jsonPreferencesGenerator, generalSettings, adminSettings, fakeScheduler);
        assertThat(viewModel.getWarning().getValue(), is(nullValue()));
    }

    @Test
    public void warning_whenServerAndAdminPasswordSet_isForBoth() {
        generalSettings.save(KEY_PASSWORD, "blah");

        adminSettings.save(KEY_ADMIN_PW, "blah");

        QRCodeViewModel viewModel = new QRCodeViewModel(qrCodeGenerator, jsonPreferencesGenerator, generalSettings, adminSettings, fakeScheduler);
        fakeScheduler.runBackground();

        assertThat(viewModel.getWarning().getValue(), is(R.string.qrcode_with_both_passwords));
    }
}