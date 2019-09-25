package com.ftp.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FtpOperationsImpl implements FtpOperations {

    private final FTPClient ftpClient;

    public FtpOperationsImpl(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public Collection<String> listFiles(String path) {
        try {
            FTPFile[] files = ftpClient.listFiles(path);
            return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void putFileToPath(File file, String path) {
        try (InputStream in = new FileInputStream(file)) {
            ftpClient.storeFile(path + "/" + file.getName(), in);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void downloadFile(String source, String destination) {
        try (FileOutputStream out = new FileOutputStream(destination)) {
            ftpClient.retrieveFile(source, out);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
