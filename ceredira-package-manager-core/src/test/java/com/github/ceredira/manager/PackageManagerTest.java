package com.github.ceredira.manager;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class PackageManagerTest {

    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    String rootFolder = "target\\tests\\" +  currentTime;

    @Test
    void init() {
        File rootFolderFile = new File(rootFolder);
        if (!rootFolderFile.exists()) {
            rootFolderFile.mkdirs();
        }

        PackageManager pm = new PackageManager(rootFolder);

        pm.init();
    }
}