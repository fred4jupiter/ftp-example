package com.ftp.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class FtpTemplateUT {

    private static final Logger LOG = LoggerFactory.getLogger(FtpTemplateUT.class);

    private static final String HOME_DIR = FakeFtpServerWrapper.HOME_DIR;

    private FakeFtpServerWrapper fakeFtpServerWrapper;

    @Before
    public void setup() {
        fakeFtpServerWrapper = new FakeFtpServerWrapper();
        fakeFtpServerWrapper.start();
    }

    @After
    public void cleanup() {
        fakeFtpServerWrapper.stop();
    }

    @Test
    public void uploadFileAndCheckResult() {
        final FtpTemplate ftpTemplate = new FtpTemplate("localhost", fakeFtpServerWrapper.getPort(), FakeFtpServerWrapper.USERNAME, FakeFtpServerWrapper.PASSWORD);
        File file = new File("src/test/resources/ftp/baz.txt");
        assertThat(file).exists();

        ftpTemplate.putFileToPath(file, HOME_DIR);

        Collection<String> uploadedFiles = ftpTemplate.listFiles(HOME_DIR);
        uploadedFiles.forEach(filename -> LOG.debug("File: {}", filename));
        assertThat(uploadedFiles).contains(file.getName());

        final String destinationPathFilename = "target/baz.txt";
        boolean result = ftpTemplate.downloadFile(HOME_DIR + "/baz.txt", destinationPathFilename);
        assertThat(result).isTrue();

        File downloadedFile = new File(destinationPathFilename);
        assertThat(downloadedFile).exists();
    }
}
