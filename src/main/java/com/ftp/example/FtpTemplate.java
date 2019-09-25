package com.ftp.example;

import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FtpTemplate implements FtpOperations {

    private final FtpClient ftpClient;

    public FtpTemplate(String server, int port, String user, String password) {
        this.ftpClient = new FtpClient(server, port, user, password);
    }

    @Override
    public Collection<String> listFiles(String path) {
        return this.ftpClient.executeOnFtp(ftpClient -> {
            FTPFile[] files = ftpClient.listFiles(path);
            return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
        });
    }

    @Override
    public void putFileToPath(File file, String path) {
        try (InputStream in = new FileInputStream(file)) {
            this.ftpClient.executeOnFtp(ftpClient -> {
                ftpClient.storeFile(path + "/" + file.getName(), in);
                return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean downloadFile(String source, String destination) {
        try (FileOutputStream out = new FileOutputStream(destination)) {
            return this.ftpClient.executeOnFtp(ftpClient -> ftpClient.retrieveFile(source, out));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
