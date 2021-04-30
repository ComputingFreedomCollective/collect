package org.gps.collect.android.activities.support;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.gps.collect.android.listeners.PermissionListener;
import org.gps.collect.android.permissions.PermissionsChecker;
import org.gps.collect.android.permissions.PermissionsProvider;
import org.gps.collect.android.storage.StorageStateProvider;

public class AlwaysGrantStoragePermissionsPermissionsProvider extends PermissionsProvider {

    public AlwaysGrantStoragePermissionsPermissionsProvider(PermissionsChecker permissionsChecker, StorageStateProvider storageStateProvider) {
        super(permissionsChecker, storageStateProvider);
    }

    @Override
    public void requestStoragePermissions(Activity activity, @NonNull PermissionListener action) {
        action.granted();
    }
}
