package org.gpssurvey.collect.android.formentry;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.gpssurvey.collect.android.formentry.saving.SaveFormProgressDialogFragment;
import org.gpssurvey.collect.android.support.RobolectricHelpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class SaveFormProgressDialogFragmentTest {

    @Test
    public void dialogIsNotCancellable() {
        FragmentScenario<SaveFormProgressDialogFragment> fragmentScenario = RobolectricHelpers.launchDialogFragment(SaveFormProgressDialogFragment.class);
        fragmentScenario.onFragment(fragment -> {
            assertThat(fragment.isCancelable(), equalTo(false));
        });
    }
}
