package com.ftp.example;

import java.io.File;
import java.util.Collection;

public interface FtpOperations {

    Collection<String> listFiles(String path);

    void putFileToPath(File file, String path);

    void downloadFile(String source, String destination);
}
