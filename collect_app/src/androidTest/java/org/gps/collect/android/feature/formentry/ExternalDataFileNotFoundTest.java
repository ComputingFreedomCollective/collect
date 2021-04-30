package org.gps.collect.android.feature.formentry;

import android.Manifest;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.gps.collect.android.R;
import org.gps.collect.android.activities.FormEntryActivity;
import org.gps.collect.android.storage.StoragePathProvider;
import org.gps.collect.android.storage.StorageSubdirectory;
import org.gps.collect.android.support.pages.FormEntryPage;
import org.gps.collect.android.support.CopyFormRule;
import org.gps.collect.android.support.ResetStateRule;
import org.gps.collect.android.support.FormLoadingUtils;

public class ExternalDataFileNotFoundTest {
    private static final String EXTERNAL_DATA_QUESTIONS = "external_data_questions.xml";

    @Rule
    public IntentsTestRule<FormEntryActivity> activityTestRule = FormLoadingUtils.getFormActivityTestRuleFor(EXTERNAL_DATA_QUESTIONS);

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            )
            .around(new ResetStateRule())
            .around(new CopyFormRule(EXTERNAL_DATA_QUESTIONS, true));

    @Test
    public void questionsThatUseExternalFiles_ShouldDisplayFriendlyMessageWhenFilesAreMissing() {
        String formsDirPath = new StoragePathProvider().getDirPath(StorageSubdirectory.FORMS);

        new FormEntryPage("externalDataQuestions", activityTestRule)
                .assertText(activityTestRule.getActivity().getString(R.string.file_missing, formsDirPath + "/external_data_questions-media/fruits.csv"))
                .swipeToNextQuestion()
                .assertText(activityTestRule.getActivity().getString(R.string.file_missing, formsDirPath + "/external_data_questions-media/itemsets.csv"));
    }
}
