package com.github.ceredira.manager;

import com.github.ceredira.model.PackageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PackageManagerTest {

    private static final String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    private static final String rootFolder = "target\\tests\\" +  currentTime;
    private static final File rootFolderFile = new File(rootFolder);

    @BeforeAll
    public static void beforeAll() {
        if (!rootFolderFile.exists()) {
            rootFolderFile.mkdirs();
        }
    }

    @Test
    public void init() {
        PackageManager pm = new PackageManager(rootFolder);

        pm.init();
    }

    @Test
    public void getPackageInfo() {
        PackageManager pm = new PackageManager(rootFolder);

        PackageInfo packageInfo = pm.info("src/test/resources/everything-1.4.1.1028-r1.yaml");

        log.info(packageInfo.toString());
    }
}