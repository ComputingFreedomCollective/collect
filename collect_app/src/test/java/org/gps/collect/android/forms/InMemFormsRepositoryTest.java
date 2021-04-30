package org.gps.collect.android.forms;

import org.junit.Before;
import org.gps.collect.android.support.InMemFormsRepository;
import org.gps.collect.utilities.Clock;

import java.io.IOException;
import java.nio.file.Files;

public class InMemFormsRepositoryTest extends FormsRepositoryTest {

    private String tempDirectory;

    @Before
    public void setup() throws IOException {
        tempDirectory = Files.createTempDirectory("forms").toString();
    }

    @Override
    public FormsRepository buildSubject() {
        return new InMemFormsRepository();
    }

    @Override
    public FormsRepository buildSubject(Clock clock) {
        return new InMemFormsRepository(clock);
    }

    @Override
    public String getFormFilesPath() {
        return tempDirectory;
    }
}
