package org.gpssurvey.collect.android.openrosa;

import org.jetbrains.annotations.Nullable;
import org.kxml2.kdom.Document;
import org.gpssurvey.collect.android.forms.FormListItem;
import org.gpssurvey.collect.android.forms.MediaFile;

import java.util.List;

public interface OpenRosaResponseParser {

    @Nullable List<FormListItem> parseFormList(Document document);
    @Nullable List<MediaFile> parseManifest(Document document);
}
