package com.github.ceredira.utils;

import com.github.ceredira.model.Repository;

import java.io.File;

public class RepositoryUtils {

    public static Repository loadRepository(String filePath) {
        File file = new File(filePath);
        Repository config = YamlUtils.loadFromFile(file, Repository.class);

        return config;
    }

    public static void saveRepository(String filePath, Repository repository) {
        File file = new File(filePath);
        YamlUtils.saveToFile(file, repository, Repository.class);
    }
}
