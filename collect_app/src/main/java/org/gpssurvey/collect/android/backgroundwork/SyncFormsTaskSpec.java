package org.gpssurvey.collect.android.backgroundwork;

import android.content.Context;

import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;
import org.gpssurvey.collect.android.analytics.Analytics;
import org.gpssurvey.collect.android.formmanagement.matchexactly.ServerFormsSynchronizer;
import org.gpssurvey.collect.android.formmanagement.matchexactly.SyncStatusRepository;
import org.gpssurvey.collect.android.forms.FormSourceException;
import org.gpssurvey.collect.android.injection.DaggerUtils;
import org.gpssurvey.collect.android.notifications.Notifier;
import org.gpssurvey.collect.async.TaskSpec;
import org.gpssurvey.collect.async.WorkerAdapter;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Named;

import static org.gpssurvey.collect.android.analytics.AnalyticsUtils.logMatchExactlyCompleted;

public class SyncFormsTaskSpec implements TaskSpec {

    @Inject
    ServerFormsSynchronizer serverFormsSynchronizer;

    @Inject
    SyncStatusRepository syncStatusRepository;

    @Inject
    Notifier notifier;

    @Inject
    @Named("FORMS")
    ChangeLock changeLock;

    @Inject
    Analytics analytics;

    @NotNull
    @Override
    public Supplier<Boolean> getTask(@NotNull Context context) {
        DaggerUtils.getComponent(context).inject(this);

        return () -> {
            changeLock.withLock((Function<Boolean, Void>) acquiredLock -> {
                if (acquiredLock) {
                    syncStatusRepository.startSync();

                    FormSourceException exception = null;
                    try {
                        serverFormsSynchronizer.synchronize();
                        syncStatusRepository.finishSync(null);
                        notifier.onSync(null);
                    } catch (FormSourceException e) {
                        exception = e;
                        syncStatusRepository.finishSync(e);
                        notifier.onSync(e);
                    }

                    logMatchExactlyCompleted(analytics, exception);
                }

                return null;
            });

            return true;
        };
    }

    @NotNull
    @Override
    public Class<? extends WorkerAdapter> getWorkManagerAdapter() {
        return Adapter.class;
    }

    public static class Adapter extends WorkerAdapter {

        public Adapter(@NotNull Context context, @NotNull WorkerParameters workerParams) {
            super(new SyncFormsTaskSpec(), context, workerParams);
        }
    }
}
