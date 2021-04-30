package org.gps.collect.android.notifications;

import org.gps.collect.android.formmanagement.ServerFormDetails;
import org.gps.collect.android.forms.FormSourceException;

import java.util.HashMap;
import java.util.List;

public interface Notifier {

    void onUpdatesAvailable(List<ServerFormDetails> updates);

    void onUpdatesDownloaded(HashMap<ServerFormDetails, String> result);

    void onSync(FormSourceException exception);

    void onSubmission(boolean failure, String message);
}
