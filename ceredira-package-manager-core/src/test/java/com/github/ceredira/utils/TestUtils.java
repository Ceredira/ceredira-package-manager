package com.github.ceredira.utils;

import com.github.ceredira.config.Config;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public static File getTestFolder() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        Config.setRootPath(new File("target/tests/", currentTime));

        File rootFolderFile = Config.getRootPath();

        if (!rootFolderFile.exists()) {
            rootFolderFile.mkdirs();
        }

        return rootFolderFile;
    }

}
