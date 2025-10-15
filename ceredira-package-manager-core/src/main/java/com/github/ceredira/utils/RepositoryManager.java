package com.github.ceredira.utils;

import com.github.ceredira.model.RepositoriesConfig;

import java.io.File;


public class RepositoryManager {



    public static RepositoriesConfig loadRepositories(String filePath) {
        File file = new File(filePath);
        RepositoriesConfig config = YamlUtil.loadFromFile(file, RepositoriesConfig.class);

        return config;
    }

    public static void saveRepositories(String filePath, RepositoriesConfig repositories) {
        File file = new File(filePath);
        YamlUtil.saveToFile(file, repositories, RepositoriesConfig.class);
    }
}
