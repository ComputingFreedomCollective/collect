package org.gps.collect.android.formentry.saving;

import android.net.Uri;

import org.gps.collect.android.analytics.Analytics;
import org.gps.collect.android.javarosawrapper.FormController;
import org.gps.collect.android.tasks.SaveFormToDisk;
import org.gps.collect.android.tasks.SaveToDiskResult;
import org.gps.collect.android.utilities.MediaUtils;

import java.util.ArrayList;

public class DiskFormSaver implements FormSaver {

    @Override
    public SaveToDiskResult save(Uri instanceContentURI, FormController formController, MediaUtils mediaUtils, boolean shouldFinalize, boolean exitAfter,
                                 String updatedSaveName, ProgressListener progressListener, Analytics analytics, ArrayList<String> tempFiles) {
        SaveFormToDisk saveFormToDisk = new SaveFormToDisk(formController, mediaUtils, exitAfter, shouldFinalize,
                updatedSaveName, instanceContentURI, analytics, tempFiles);
        return saveFormToDisk.saveForm(progressListener);
    }
}
