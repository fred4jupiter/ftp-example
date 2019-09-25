package com.ftp.example;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

class FtpClient {

    private static final Logger LOG = LoggerFactory.getLogger(FtpClient.class);

    private final String server;
    private final int port;
    private final String user;
    private final String password;

    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    private FTPClient createAndOpenConnection() throws IOException {
        LOG.debug("open...");
        FTPClient tmpClient = new FTPClient();

        tmpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        tmpClient.connect(server, port);
        int reply = tmpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            tmpClient.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        LOG.debug("login...");
        tmpClient.login(user, password);
        tmpClient.enterLocalPassiveMode();
        return tmpClient;
    }

    public <T> T executeOnFtp(FtpCallback<T> callback) {
        try {
            FTPClient client = createAndOpenConnection();
            T result = callback.operate(client);
            LOG.debug("close...");
            client.disconnect();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @FunctionalInterface
    public interface FtpCallback<T> {

        T operate(FTPClient ftpclient) throws IOException;
    }
}
