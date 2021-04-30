package org.gps.collect.android.injection.config;

import android.app.Application;

import org.javarosa.core.reference.ReferenceManager;
import org.gps.collect.android.activities.CollectAbstractActivity;
import org.gps.collect.android.activities.DeleteSavedFormActivity;
import org.gps.collect.android.activities.FillBlankFormActivity;
import org.gps.collect.android.activities.FormDownloadListActivity;
import org.gps.collect.android.activities.FormEntryActivity;
import org.gps.collect.android.activities.FormHierarchyActivity;
import org.gps.collect.android.activities.FormMapActivity;
import org.gps.collect.android.activities.GeoPointMapActivity;
import org.gps.collect.android.activities.GeoPolyActivity;
import org.gps.collect.android.activities.InstanceUploaderActivity;
import org.gps.collect.android.activities.InstanceUploaderListActivity;
import org.gps.collect.android.activities.MainMenuActivity;
import org.gps.collect.android.activities.SplashScreenActivity;
import org.gps.collect.android.adapters.InstanceUploaderAdapter;
import org.gps.collect.android.analytics.Analytics;
import org.gps.collect.android.application.Collect;
import org.gps.collect.android.application.initialization.ApplicationInitializer;
import org.gps.collect.android.audio.AudioRecordingControllerFragment;
import org.gps.collect.android.audio.AudioRecordingErrorDialogFragment;
import org.gps.collect.android.backgroundwork.AutoSendTaskSpec;
import org.gps.collect.android.backgroundwork.AutoUpdateTaskSpec;
import org.gps.collect.android.backgroundwork.SyncFormsTaskSpec;
import org.gps.collect.android.configure.SettingsImporter;
import org.gps.collect.android.configure.qr.QRCodeScannerFragment;
import org.gps.collect.android.configure.qr.QRCodeTabsActivity;
import org.gps.collect.android.configure.qr.ShowQRCodeFragment;
import org.gps.collect.android.formentry.BackgroundAudioPermissionDialogFragment;
import org.gps.collect.android.formentry.ODKView;
import org.gps.collect.android.formentry.QuitFormDialogFragment;
import org.gps.collect.android.formentry.saving.SaveAnswerFileErrorDialogFragment;
import org.gps.collect.android.formentry.saving.SaveFormProgressDialogFragment;
import org.gps.collect.android.fragments.BarCodeScannerFragment;
import org.gps.collect.android.fragments.BlankFormListFragment;
import org.gps.collect.android.fragments.MapBoxInitializationFragment;
import org.gps.collect.android.fragments.SavedFormListFragment;
import org.gps.collect.android.fragments.dialogs.SelectMinimalDialog;
import org.gps.collect.android.gdrive.GoogleDriveActivity;
import org.gps.collect.android.gdrive.GoogleSheetsUploaderActivity;
import org.gps.collect.android.geo.GoogleMapFragment;
import org.gps.collect.android.geo.MapboxMapFragment;
import org.gps.collect.android.geo.OsmDroidMapFragment;
import org.gps.collect.android.logic.PropertyManager;
import org.gps.collect.android.openrosa.OpenRosaHttpInterface;
import org.gps.collect.android.preferences.AdminPasswordDialogFragment;
import org.gps.collect.android.preferences.AdminPreferencesFragment;
import org.gps.collect.android.preferences.AdminSharedPreferences;
import org.gps.collect.android.preferences.BasePreferenceFragment;
import org.gps.collect.android.preferences.ExperimentalPreferencesFragment;
import org.gps.collect.android.preferences.FormManagementPreferences;
import org.gps.collect.android.preferences.FormMetadataFragment;
import org.gps.collect.android.preferences.GeneralPreferencesFragment;
import org.gps.collect.android.preferences.GeneralSharedPreferences;
import org.gps.collect.android.preferences.IdentityPreferences;
import org.gps.collect.android.preferences.PreferencesActivity;
import org.gps.collect.android.preferences.PreferencesProvider;
import org.gps.collect.android.preferences.ServerAuthDialogFragment;
import org.gps.collect.android.preferences.ServerPreferencesFragment;
import org.gps.collect.android.preferences.UserInterfacePreferencesFragment;
import org.gps.collect.android.provider.FormsProvider;
import org.gps.collect.android.provider.InstanceProvider;
import org.gps.collect.android.storage.StorageInitializer;
import org.gps.collect.android.storage.migration.StorageMigrationDialog;
import org.gps.collect.android.storage.migration.StorageMigrationService;
import org.gps.collect.android.tasks.InstanceServerUploaderTask;
import org.gps.collect.android.utilities.ApplicationResetter;
import org.gps.collect.android.utilities.AuthDialogUtility;
import org.gps.collect.android.widgets.ExStringWidget;
import org.gps.collect.android.widgets.QuestionWidget;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Dagger component for the application. Should include
 * application level Dagger Modules and be built with Application
 * object.
 *
 * Add an `inject(MyClass myClass)` method here for objects you want
 * to inject into so Dagger knows to wire it up.
 *
 * Annotated with @Singleton so modules can include @Singletons that will
 * be retained at an application level (as this an instance of this components
 * is owned by the Application object).
 *
 * If you need to call a provider directly from the component (in a test
 * for example) you can add a method with the type you are looking to fetch
 * (`MyType myType()`) to this interface.
 *
 * To read more about Dagger visit: https://google.github.io/dagger/users-guide
 **/

@Singleton
@Component(modules = {
        AppDependencyModule.class
})
public interface AppDependencyComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder appDependencyModule(AppDependencyModule testDependencyModule);

        AppDependencyComponent build();
    }

    void inject(Collect collect);

    void inject(InstanceUploaderAdapter instanceUploaderAdapter);

    void inject(SavedFormListFragment savedFormListFragment);

    void inject(PropertyManager propertyManager);

    void inject(FormEntryActivity formEntryActivity);

    void inject(InstanceServerUploaderTask uploader);

    void inject(ServerPreferencesFragment serverPreferencesFragment);

    void inject(AuthDialogUtility authDialogUtility);

    void inject(FormDownloadListActivity formDownloadListActivity);

    void inject(InstanceUploaderListActivity activity);

    void inject(GoogleDriveActivity googleDriveActivity);

    void inject(GoogleSheetsUploaderActivity googleSheetsUploaderActivity);

    void inject(QuestionWidget questionWidget);

    void inject(ExStringWidget exStringWidget);

    void inject(ODKView odkView);

    void inject(FormMetadataFragment formMetadataFragment);

    void inject(GeoPointMapActivity geoMapActivity);

    void inject(GeoPolyActivity geoPolyActivity);

    void inject(FormMapActivity formMapActivity);

    void inject(OsmDroidMapFragment mapFragment);

    void inject(GoogleMapFragment mapFragment);

    void inject(MapboxMapFragment mapFragment);

    void inject(MainMenuActivity mainMenuActivity);

    void inject(QRCodeTabsActivity qrCodeTabsActivity);

    void inject(ShowQRCodeFragment showQRCodeFragment);

    void inject(StorageInitializer storageInitializer);

    void inject(StorageMigrationService storageMigrationService);

    void inject(AutoSendTaskSpec autoSendTaskSpec);

    void inject(StorageMigrationDialog storageMigrationDialog);

    void inject(AdminPasswordDialogFragment adminPasswordDialogFragment);

    void inject(SplashScreenActivity splashScreenActivity);

    void inject(FormHierarchyActivity formHierarchyActivity);

    void inject(FormManagementPreferences formManagementPreferences);

    void inject(IdentityPreferences identityPreferences);

    void inject(UserInterfacePreferencesFragment userInterfacePreferencesFragment);

    void inject(SaveFormProgressDialogFragment saveFormProgressDialogFragment);

    void inject(QuitFormDialogFragment quitFormDialogFragment);

    void inject(BarCodeScannerFragment barCodeScannerFragment);

    void inject(QRCodeScannerFragment qrCodeScannerFragment);

    void inject(PreferencesActivity preferencesActivity);

    void inject(ApplicationResetter applicationResetter);

    void inject(FillBlankFormActivity fillBlankFormActivity);

    void inject(MapBoxInitializationFragment mapBoxInitializationFragment);

    void inject(SyncFormsTaskSpec syncWork);

    void inject(ExperimentalPreferencesFragment experimentalPreferencesFragment);

    void inject(AutoUpdateTaskSpec autoUpdateTaskSpec);

    void inject(ServerAuthDialogFragment serverAuthDialogFragment);

    void inject(BasePreferenceFragment basePreferenceFragment);

    void inject(BlankFormListFragment blankFormListFragment);

    void inject(InstanceUploaderActivity instanceUploaderActivity);

    void inject(GeneralPreferencesFragment generalPreferencesFragment);

    void inject(DeleteSavedFormActivity deleteSavedFormActivity);

    void inject(AdminPreferencesFragment.MainMenuAccessPreferences mainMenuAccessPreferences);

    void inject(SelectMinimalDialog selectMinimalDialog);

    void inject(AudioRecordingControllerFragment audioRecordingControllerFragment);

    void inject(SaveAnswerFileErrorDialogFragment saveAnswerFileErrorDialogFragment);

    void inject(AudioRecordingErrorDialogFragment audioRecordingErrorDialogFragment);

    void inject(CollectAbstractActivity collectAbstractActivity);

    void inject(FormsProvider formsProvider);

    void inject(InstanceProvider instanceProvider);

    void inject(BackgroundAudioPermissionDialogFragment backgroundAudioPermissionDialogFragment);

    OpenRosaHttpInterface openRosaHttpInterface();

    ReferenceManager referenceManager();

    Analytics analytics();

    GeneralSharedPreferences generalSharedPreferences();

    AdminSharedPreferences adminSharedPreferences();

    PreferencesProvider preferencesProvider();

    ApplicationInitializer applicationInitializer();

    SettingsImporter settingsImporter();
}
