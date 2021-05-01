package org.gpssurvey.collect.android.formmanagement;

import org.gpssurvey.collect.android.forms.Form;
import org.gpssurvey.collect.android.forms.FormsRepository;
import org.gpssurvey.collect.android.instances.Instance;
import org.gpssurvey.collect.android.instances.InstancesRepository;

import java.util.List;

public class FormDeleter {

    private final FormsRepository formsRepository;
    private final InstancesRepository instancesRepository;

    public FormDeleter(FormsRepository formsRepository, InstancesRepository instancesRepository) {
        this.formsRepository = formsRepository;
        this.instancesRepository = instancesRepository;
    }

    public void delete(Long id) {
        Form form = formsRepository.get(id);

        List<Instance> instancesForVersion = instancesRepository.getAllNotDeletedByFormIdAndVersion(form.getJrFormId(), form.getJrVersion());
        // If there's more than one form with the same formid/version, trust the user that they want to truly delete this one
        // because otherwise it may not ever be removed (instance deletion only deletes one corresponding form).
        List<Form> formsWithSameFormIdVersion = formsRepository.getAllByFormIdAndVersion(form.getJrFormId(), form.getJrVersion());
        if (instancesForVersion.isEmpty() || formsWithSameFormIdVersion.size() > 1) {
            formsRepository.delete(id);
        } else {
            formsRepository.softDelete(form.getId());
        }
    }
}
