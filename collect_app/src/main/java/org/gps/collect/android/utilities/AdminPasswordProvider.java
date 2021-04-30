package org.gps.collect.android.utilities;

import org.gps.collect.android.preferences.AdminSharedPreferences;

import static org.gps.collect.android.preferences.AdminKeys.KEY_ADMIN_PW;

public class AdminPasswordProvider {
    private final AdminSharedPreferences adminSharedPreferences;

    public AdminPasswordProvider(AdminSharedPreferences adminSharedPreferences) {
        this.adminSharedPreferences = adminSharedPreferences;
    }

    public boolean isAdminPasswordSet() {
        String adminPassword = getAdminPassword();
        return adminPassword != null && !adminPassword.isEmpty();
    }

    public String getAdminPassword() {
        return (String) adminSharedPreferences.get(KEY_ADMIN_PW);
    }
}
