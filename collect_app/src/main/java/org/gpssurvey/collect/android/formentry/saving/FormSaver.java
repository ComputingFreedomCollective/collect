package org.gpssurvey.collect.android.formentry.saving;

import android.net.Uri;

import org.gpssurvey.collect.android.analytics.Analytics;
import org.gpssurvey.collect.android.javarosawrapper.FormController;
import org.gpssurvey.collect.android.tasks.SaveToDiskResult;
import org.gpssurvey.collect.android.utilities.MediaUtils;

import java.util.ArrayList;

public interface FormSaver {
    SaveToDiskResult save(Uri instanceContentURI, FormController formController, MediaUtils mediaUtils, boolean shouldFinalize, boolean exitAfter,
                          String updatedSaveName, ProgressListener progressListener, Analytics analytics, ArrayList<String> tempFiles);

    interface ProgressListener {
        void onProgressUpdate(String message);
    }
}
