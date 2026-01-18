package com.github.ceredira.config;

import lombok.Setter;

import java.io.File;
import java.nio.file.Path;

// ToDo нужно обсудить: стоит ли getRootPath() оставлять File?
//  Такой подход добавляет преобразования в Path и обратно в File
public class Config {
    @Setter
    private static File rootPath;

    public static File getRootPath() {
        if (rootPath == null) {
            if (System.getProperties().containsKey("rootPath")) {
                rootPath = new File(System.getProperty("rootPath"));
            } else {
                throw new RuntimeException("Parameter rootPath must be initialized before first use!");
            }
        }

        return rootPath;
    }

    public static Path getRootPathAsPath() {
        return getRootPath().toPath();
    }

    public static File getFileFromRoot(String path) {
        return new File(getRootPath(), path);
    }
}