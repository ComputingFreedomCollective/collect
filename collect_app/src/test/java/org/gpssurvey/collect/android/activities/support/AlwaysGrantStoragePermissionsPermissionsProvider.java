package org.gpssurvey.collect.android.activities.support;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.gpssurvey.collect.android.listeners.PermissionListener;
import org.gpssurvey.collect.android.permissions.PermissionsChecker;
import org.gpssurvey.collect.android.permissions.PermissionsProvider;
import org.gpssurvey.collect.android.storage.StorageStateProvider;

public class AlwaysGrantStoragePermissionsPermissionsProvider extends PermissionsProvider {

    public AlwaysGrantStoragePermissionsPermissionsProvider(PermissionsChecker permissionsChecker, StorageStateProvider storageStateProvider) {
        super(permissionsChecker, storageStateProvider);
    }

    @Override
    public void requestStoragePermissions(Activity activity, @NonNull PermissionListener action) {
        action.granted();
    }
}
