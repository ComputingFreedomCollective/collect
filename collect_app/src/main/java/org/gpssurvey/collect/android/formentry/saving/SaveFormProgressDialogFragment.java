package org.gpssurvey.collect.android.formentry.saving;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import org.gpssurvey.collect.android.R;
import org.gpssurvey.collect.android.analytics.Analytics;
import org.gpssurvey.collect.android.fragments.dialogs.ProgressDialogFragment;
import org.gpssurvey.collect.android.injection.DaggerUtils;
import org.gpssurvey.collect.async.Scheduler;

import javax.inject.Inject;

import static org.gpssurvey.collect.android.formentry.saving.FormSaveViewModel.SaveResult.State.SAVING;

public class SaveFormProgressDialogFragment extends ProgressDialogFragment {

    @Inject
    Analytics analytics;

    @Inject
    Scheduler scheduler;

    @Inject
    FormSaveViewModel.FactoryFactory formSaveViewModelFactoryFactory;

    private FormSaveViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        DaggerUtils.getComponent(context).inject(this);

        ViewModelProvider.Factory factory = formSaveViewModelFactoryFactory.create(requireActivity(), null);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(FormSaveViewModel.class);

        setCancelable(false);
        setTitle(getString(R.string.saving_form));

        viewModel.getSaveResult().observe(this, result -> {
            if (result != null && result.getState() == SAVING && result.getMessage() != null) {
                setMessage(getString(R.string.please_wait) + "\n\n" + result.getMessage());
            } else {
                setMessage(getString(R.string.please_wait));
            }
        });
    }

    @Override
    protected Cancellable getCancellable() {
        return viewModel;
    }
}
