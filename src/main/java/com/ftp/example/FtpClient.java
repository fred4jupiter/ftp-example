package com.ftp.example;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

class FtpClient {

    private static final Logger LOG = LoggerFactory.getLogger(FtpClient.class );

    private final String server;
    private final int port;
    private final String user;
    private final String password;
    private FTPClient ftp;

    FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    void open() throws IOException {
        LOG.debug("open...");
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        LOG.debug("login...");
        ftp.login(user, password);
        ftp.enterLocalPassiveMode();
    }

    void close() throws IOException {
        LOG.debug("close...");
        ftp.disconnect();
    }

    public FTPClient getFtp() {
        return ftp;
    }
}
