package com.ftp.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class FtpTemplateTest {

    private static final Logger LOG = LoggerFactory.getLogger(FtpTemplateTest.class);

    @Test
    public void uploadFile() {
        FtpTemplate ftpTemplate = new FtpTemplate("192.168.75.75", 21, "vagrant", "vagrant");
        File file = new File("src/test/resources/ftp/baz.txt");
        assertThat(file).exists();
        ftpTemplate.executeOnFtp(callback -> {
            callback.putFileToPath(file, "/home/vagrant");
            Collection<String> uploadedFiles = callback.listFiles("/home/vagrant");
            uploadedFiles.forEach(filename -> LOG.debug("File: {}", filename));
            assertThat(uploadedFiles).contains(file.getName());
        });
    }
}
