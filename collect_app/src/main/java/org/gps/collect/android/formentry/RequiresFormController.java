package org.gps.collect.android.formentry;

import androidx.annotation.NonNull;

import org.gps.collect.android.javarosawrapper.FormController;

public interface RequiresFormController {
    void formLoaded(@NonNull FormController formController);
}
