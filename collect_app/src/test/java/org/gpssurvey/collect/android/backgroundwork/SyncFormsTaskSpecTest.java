package org.gpssurvey.collect.android.backgroundwork;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.gpssurvey.collect.android.analytics.Analytics;
import org.gpssurvey.collect.android.analytics.AnalyticsEvents;
import org.gpssurvey.collect.android.formmanagement.FormDownloader;
import org.gpssurvey.collect.android.formmanagement.ServerFormsDetailsFetcher;
import org.gpssurvey.collect.android.formmanagement.matchexactly.ServerFormsSynchronizer;
import org.gpssurvey.collect.android.formmanagement.matchexactly.SyncStatusRepository;
import org.gpssurvey.collect.android.forms.FormsRepository;
import org.gpssurvey.collect.android.injection.config.AppDependencyModule;
import org.gpssurvey.collect.android.instances.InstancesRepository;
import org.gpssurvey.collect.android.notifications.Notifier;
import org.gpssurvey.collect.android.forms.FormSourceException;
import org.gpssurvey.collect.android.preferences.GeneralSharedPreferences;
import org.gpssurvey.collect.android.preferences.PreferencesProvider;
import org.gpssurvey.collect.android.support.BooleanChangeLock;
import org.gpssurvey.collect.android.support.RobolectricHelpers;
import org.robolectric.RobolectricTestRunner;

import java.util.function.Supplier;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(RobolectricTestRunner.class)
public class SyncFormsTaskSpecTest {

    private final ServerFormsSynchronizer serverFormsSynchronizer = mock(ServerFormsSynchronizer.class);
    private final SyncStatusRepository syncStatusRepository = mock(SyncStatusRepository.class);
    private final Notifier notifier = mock(Notifier.class);
    private final Analytics analytics = mock(Analytics.class);
    private final BooleanChangeLock changeLock = new BooleanChangeLock();

    @Before
    public void setup() {
        RobolectricHelpers.overrideAppDependencyModule(new AppDependencyModule() {

            @Override
            public ChangeLock providesFormsChangeLock() {
                return changeLock;
            }

            @Override
            public ServerFormsSynchronizer providesServerFormSynchronizer(ServerFormsDetailsFetcher serverFormsDetailsFetcher, FormsRepository formsRepository, FormDownloader formDownloader, InstancesRepository instancesRepository) {
                return serverFormsSynchronizer;
            }

            @Override
            public SyncStatusRepository providesServerFormSyncRepository() {
                return syncStatusRepository;
            }

            @Override
            public Notifier providesNotifier(Application application, PreferencesProvider preferencesProvider) {
                return notifier;
            }

            @Override
            public Analytics providesAnalytics(Application application, GeneralSharedPreferences generalSharedPreferences) {
                return analytics;
            }
        });
    }

    @Test
    public void setsRepositoryToSyncing_runsSync_thenSetsRepositoryToNotSyncingAndNotifies() throws Exception {
        InOrder inOrder = inOrder(syncStatusRepository, serverFormsSynchronizer);

        SyncFormsTaskSpec taskSpec = new SyncFormsTaskSpec();
        Supplier<Boolean> task = taskSpec.getTask(ApplicationProvider.getApplicationContext());
        task.get();

        inOrder.verify(syncStatusRepository).startSync();
        inOrder.verify(serverFormsSynchronizer).synchronize();
        inOrder.verify(syncStatusRepository).finishSync(null);

        verify(notifier).onSync(null);
    }

    @Test
    public void logsAnalytics() {
        SyncFormsTaskSpec taskSpec = new SyncFormsTaskSpec();
        Supplier<Boolean> task = taskSpec.getTask(ApplicationProvider.getApplicationContext());
        task.get();

        verify(analytics).logEvent(AnalyticsEvents.MATCH_EXACTLY_SYNC_COMPLETED, "Success");
    }

    @Test
    public void whenSynchronizingFails_setsRepositoryToNotSyncingAndNotifiesWithError() throws Exception {
        FormSourceException exception = new FormSourceException.FetchError();
        doThrow(exception).when(serverFormsSynchronizer).synchronize();
        InOrder inOrder = inOrder(syncStatusRepository, serverFormsSynchronizer);

        SyncFormsTaskSpec taskSpec = new SyncFormsTaskSpec();
        Supplier<Boolean> task = taskSpec.getTask(ApplicationProvider.getApplicationContext());
        task.get();

        inOrder.verify(syncStatusRepository).startSync();
        inOrder.verify(serverFormsSynchronizer).synchronize();
        inOrder.verify(syncStatusRepository).finishSync(exception);

        verify(notifier).onSync(exception);
    }

    @Test
    public void whenSynchronizingFails_logsAnalytics() throws Exception {
        FormSourceException exception = new FormSourceException.FetchError();
        doThrow(exception).when(serverFormsSynchronizer).synchronize();

        SyncFormsTaskSpec taskSpec = new SyncFormsTaskSpec();
        Supplier<Boolean> task = taskSpec.getTask(ApplicationProvider.getApplicationContext());
        task.get();

        verify(analytics).logEvent(AnalyticsEvents.MATCH_EXACTLY_SYNC_COMPLETED, "FETCH_ERROR");
    }

    @Test
    public void whenChangeLockLocked_doesNothing() {
        changeLock.lock();

        SyncFormsTaskSpec taskSpec = new SyncFormsTaskSpec();
        Supplier<Boolean> task = taskSpec.getTask(ApplicationProvider.getApplicationContext());
        task.get();

        verifyNoInteractions(serverFormsSynchronizer);
        verifyNoInteractions(syncStatusRepository);
        verifyNoInteractions(notifier);
    }
}
