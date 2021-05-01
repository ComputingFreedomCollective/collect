package org.gpssurvey.collect.android.injection.config;

import android.app.Application;

import org.javarosa.core.reference.ReferenceManager;
import org.gpssurvey.collect.android.activities.CollectAbstractActivity;
import org.gpssurvey.collect.android.activities.DeleteSavedFormActivity;
import org.gpssurvey.collect.android.activities.FillBlankFormActivity;
import org.gpssurvey.collect.android.activities.FormDownloadListActivity;
import org.gpssurvey.collect.android.activities.FormEntryActivity;
import org.gpssurvey.collect.android.activities.FormHierarchyActivity;
import org.gpssurvey.collect.android.activities.FormMapActivity;
import org.gpssurvey.collect.android.activities.GeoPointMapActivity;
import org.gpssurvey.collect.android.activities.GeoPolyActivity;
import org.gpssurvey.collect.android.activities.InstanceUploaderActivity;
import org.gpssurvey.collect.android.activities.InstanceUploaderListActivity;
import org.gpssurvey.collect.android.activities.MainMenuActivity;
import org.gpssurvey.collect.android.activities.SplashScreenActivity;
import org.gpssurvey.collect.android.adapters.InstanceUploaderAdapter;
import org.gpssurvey.collect.android.analytics.Analytics;
import org.gpssurvey.collect.android.application.Collect;
import org.gpssurvey.collect.android.application.initialization.ApplicationInitializer;
import org.gpssurvey.collect.android.audio.AudioRecordingControllerFragment;
import org.gpssurvey.collect.android.audio.AudioRecordingErrorDialogFragment;
import org.gpssurvey.collect.android.backgroundwork.AutoSendTaskSpec;
import org.gpssurvey.collect.android.backgroundwork.AutoUpdateTaskSpec;
import org.gpssurvey.collect.android.backgroundwork.SyncFormsTaskSpec;
import org.gpssurvey.collect.android.configure.SettingsImporter;
import org.gpssurvey.collect.android.configure.qr.QRCodeScannerFragment;
import org.gpssurvey.collect.android.configure.qr.QRCodeTabsActivity;
import org.gpssurvey.collect.android.configure.qr.ShowQRCodeFragment;
import org.gpssurvey.collect.android.formentry.BackgroundAudioPermissionDialogFragment;
import org.gpssurvey.collect.android.formentry.ODKView;
import org.gpssurvey.collect.android.formentry.QuitFormDialogFragment;
import org.gpssurvey.collect.android.formentry.saving.SaveAnswerFileErrorDialogFragment;
import org.gpssurvey.collect.android.formentry.saving.SaveFormProgressDialogFragment;
import org.gpssurvey.collect.android.fragments.BarCodeScannerFragment;
import org.gpssurvey.collect.android.fragments.BlankFormListFragment;
import org.gpssurvey.collect.android.fragments.MapBoxInitializationFragment;
import org.gpssurvey.collect.android.fragments.SavedFormListFragment;
import org.gpssurvey.collect.android.fragments.dialogs.SelectMinimalDialog;
import org.gpssurvey.collect.android.gdrive.GoogleDriveActivity;
import org.gpssurvey.collect.android.gdrive.GoogleSheetsUploaderActivity;
import org.gpssurvey.collect.android.geo.GoogleMapFragment;
import org.gpssurvey.collect.android.geo.MapboxMapFragment;
import org.gpssurvey.collect.android.geo.OsmDroidMapFragment;
import org.gpssurvey.collect.android.logic.PropertyManager;
import org.gpssurvey.collect.android.openrosa.OpenRosaHttpInterface;
import org.gpssurvey.collect.android.preferences.AdminPasswordDialogFragment;
import org.gpssurvey.collect.android.preferences.AdminPreferencesFragment;
import org.gpssurvey.collect.android.preferences.AdminSharedPreferences;
import org.gpssurvey.collect.android.preferences.BasePreferenceFragment;
import org.gpssurvey.collect.android.preferences.ExperimentalPreferencesFragment;
import org.gpssurvey.collect.android.preferences.FormManagementPreferences;
import org.gpssurvey.collect.android.preferences.FormMetadataFragment;
import org.gpssurvey.collect.android.preferences.GeneralPreferencesFragment;
import org.gpssurvey.collect.android.preferences.GeneralSharedPreferences;
import org.gpssurvey.collect.android.preferences.IdentityPreferences;
import org.gpssurvey.collect.android.preferences.PreferencesActivity;
import org.gpssurvey.collect.android.preferences.PreferencesProvider;
import org.gpssurvey.collect.android.preferences.ServerAuthDialogFragment;
import org.gpssurvey.collect.android.preferences.ServerPreferencesFragment;
import org.gpssurvey.collect.android.preferences.UserInterfacePreferencesFragment;
import org.gpssurvey.collect.android.provider.FormsProvider;
import org.gpssurvey.collect.android.provider.InstanceProvider;
import org.gpssurvey.collect.android.storage.StorageInitializer;
import org.gpssurvey.collect.android.storage.migration.StorageMigrationDialog;
import org.gpssurvey.collect.android.storage.migration.StorageMigrationService;
import org.gpssurvey.collect.android.tasks.InstanceServerUploaderTask;
import org.gpssurvey.collect.android.utilities.ApplicationResetter;
import org.gpssurvey.collect.android.utilities.AuthDialogUtility;
import org.gpssurvey.collect.android.widgets.ExStringWidget;
import org.gpssurvey.collect.android.widgets.QuestionWidget;

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
