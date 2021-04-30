package org.gps.collect.android.configure.qr;

import android.content.Context;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeResult;

import org.gps.collect.android.R;
import org.gps.collect.android.activities.MainMenuActivity;
import org.gps.collect.android.analytics.Analytics;
import org.gps.collect.android.analytics.AnalyticsEvents;
import org.gps.collect.android.configure.SettingsImporter;
import org.gps.collect.android.fragments.BarCodeScannerFragment;
import org.gps.collect.android.injection.DaggerUtils;
import org.gps.collect.android.utilities.FileUtils;
import org.gps.collect.android.utilities.ToastUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.zip.DataFormatException;

import javax.inject.Inject;

import static org.gps.collect.android.activities.ActivityUtils.startActivityAndCloseAllOthers;
import static org.gps.collect.android.utilities.CompressionUtils.decompress;

public class QRCodeScannerFragment extends BarCodeScannerFragment {

    @Inject
    SettingsImporter settingsImporter;

    @Inject
    Analytics analytics;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerUtils.getComponent(context).inject(this);
    }

    @Override
    protected void handleScanningResult(BarcodeResult result) throws IOException, DataFormatException {
        boolean importSuccess = settingsImporter.fromJSON(decompress(result.getText()));
        String settingsHash = FileUtils.getMd5Hash(new ByteArrayInputStream(result.getText().getBytes()));

        if (importSuccess) {
            ToastUtils.showLongToast(getString(R.string.successfully_imported_settings));
            analytics.logEvent(AnalyticsEvents.SETTINGS_IMPORT_QR, "Success", settingsHash);
            startActivityAndCloseAllOthers(requireActivity(), MainMenuActivity.class);
        } else {
            ToastUtils.showLongToast(getString(R.string.invalid_qrcode));
            analytics.logEvent(AnalyticsEvents.SETTINGS_IMPORT_QR, "No valid settings", settingsHash);
        }
    }

    @Override
    protected Collection<String> getSupportedCodeFormats() {
        return Collections.singletonList(IntentIntegrator.QR_CODE);
    }
}
