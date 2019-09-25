package com.ftp.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class FtpTemplateMT {

    private static final Logger LOG = LoggerFactory.getLogger(FtpTemplateMT.class);

    @Test
    public void uploadFileAndCheckResult() {
        final FtpTemplate ftpTemplate = new FtpTemplate("192.168.75.75", 21, "vagrant", "vagrant");
        File file = new File("src/test/resources/ftp/baz.txt");
        assertThat(file).exists();

        ftpTemplate.putFileToPath(file, "/home/vagrant");

        Collection<String> uploadedFiles = ftpTemplate.listFiles("/home/vagrant");
        uploadedFiles.forEach(filename -> LOG.debug("File: {}", filename));
        assertThat(uploadedFiles).contains(file.getName());

        final String destinationPathFilename = "target/baz.txt";
        boolean result = ftpTemplate.downloadFile("/home/vagrant/baz.txt", destinationPathFilename);
        assertThat(result).isTrue();

        File downloadedFile = new File(destinationPathFilename);
        assertThat(downloadedFile).exists();
    }
}
