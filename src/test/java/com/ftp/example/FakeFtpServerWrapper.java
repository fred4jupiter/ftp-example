package com.ftp.example;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class FakeFtpServerWrapper {

    public static final String USERNAME = "jack";
    public static final String PASSWORD = "johnson";
    public static final String HOME_DIR = "/home/jack";

    private final FakeFtpServer fakeFtpServer;

    public FakeFtpServerWrapper() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(USERNAME, PASSWORD, HOME_DIR));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry(HOME_DIR));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);
    }

    public void start() {
        fakeFtpServer.start();
    }

    public void stop() {
        fakeFtpServer.stop();
    }

    public int getPort() {
        return fakeFtpServer.getServerControlPort();
    }
}
