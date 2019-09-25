package com.ftp.example;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FtpTemplate {

    private final FtpClient ftpClient;

    public FtpTemplate() {
        this("localhost", 21, "mega", "mega");
    }

    public FtpTemplate(String server, int port, String user, String password) {
        this.ftpClient = new FtpClient(server, port, user, password);
    }

    public void executeOnFtp(FtpCallback callback) {
        try {
            ftpClient.open();
            callback.operate(new FtpOperationsImpl(ftpClient.getFtp()));
            ftpClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @FunctionalInterface
    public interface FtpCallback {

        void operate(FtpOperations ftpOperations);
    }
}
