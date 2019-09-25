package com.ftp.example;

import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class FtpTemplateTest {

    @Test
    public void uploadFile() {
        FtpTemplate ftpTemplate = new FtpTemplate("192.168.75.75", 21, "vagrant", "vagrant");
        File file = new File("src/test/resources/ftp/baz.txt");
        assertThat(file).exists();
        ftpTemplate.executeOnFtp(callback -> {
            callback.putFileToPath(file, "/home/vagrant");
            Collection<String> uploadedFiles = callback.listFiles("/home/vagrant");
            uploadedFiles.stream().forEach(filename -> System.out.println("File: " + filename));
        });

    }
}
