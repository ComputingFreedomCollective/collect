package org.gpssurvey.collect.android.formmanagement.matchexactly;

import org.gpssurvey.collect.android.formmanagement.FormDeleter;
import org.gpssurvey.collect.android.formmanagement.FormDownloadException;
import org.gpssurvey.collect.android.formmanagement.FormDownloader;
import org.gpssurvey.collect.android.formmanagement.ServerFormDetails;
import org.gpssurvey.collect.android.formmanagement.ServerFormsDetailsFetcher;
import org.gpssurvey.collect.android.forms.Form;
import org.gpssurvey.collect.android.forms.FormSourceException;
import org.gpssurvey.collect.android.forms.FormsRepository;
import org.gpssurvey.collect.android.instances.InstancesRepository;

import java.util.List;

public class ServerFormsSynchronizer {

    private final FormsRepository formsRepository;
    private final InstancesRepository instancesRepository;
    private final FormDownloader formDownloader;
    private final ServerFormsDetailsFetcher serverFormsDetailsFetcher;

    public ServerFormsSynchronizer(ServerFormsDetailsFetcher serverFormsDetailsFetcher, FormsRepository formsRepository, InstancesRepository instancesRepository, FormDownloader formDownloader) {
        this.serverFormsDetailsFetcher = serverFormsDetailsFetcher;
        this.formsRepository = formsRepository;
        this.instancesRepository = instancesRepository;
        this.formDownloader = formDownloader;
    }

    public void synchronize() throws FormSourceException {
        List<ServerFormDetails> formList = serverFormsDetailsFetcher.fetchFormDetails();
        List<Form> formsOnDevice = formsRepository.getAll();
        FormDeleter formDeleter = new FormDeleter(formsRepository, instancesRepository);

        formsOnDevice.stream().forEach(form -> {
            if (formList.stream().noneMatch(f -> form.getJrFormId().equals(f.getFormId()))) {
                formDeleter.delete(form.getId());
            }
        });

        boolean downloadException = false;

        for (ServerFormDetails form : formList) {
            if (form.isNotOnDevice() || form.isUpdated()) {
                try {
                    formDownloader.downloadForm(form, null, null);
                } catch (FormDownloadException e) {
                    downloadException = true;
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        if (downloadException) {
            throw new FormSourceException.FetchError();
        }
    }
}
