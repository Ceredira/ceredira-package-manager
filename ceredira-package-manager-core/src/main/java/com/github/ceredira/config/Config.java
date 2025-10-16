package com.github.ceredira.config;

import lombok.Setter;

import java.io.File;

public class Config {
    @Setter
    private static File rootPath;

    public static File getRootPath() {
        if (rootPath == null) {
            throw new RuntimeException("Parameter rootPath must be initialized before first use!");
        }

        return rootPath;
    }

    public static String getRootPathString() {
        return getRootPath().getPath();
    }

    public static File getFileFromRoot(String path) {
        return new File(getRootPath(), path);
    }
}