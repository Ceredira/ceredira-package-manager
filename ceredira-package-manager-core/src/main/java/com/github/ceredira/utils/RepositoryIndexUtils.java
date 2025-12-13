package com.github.ceredira.utils;

import com.github.ceredira.model.RepositoryIndex;

import java.io.File;

public class RepositoryIndexUtils {
    public static RepositoryIndex getRepositoryIndex(String filePath) {
        File file = new File(filePath);
        RepositoryIndex repositoryIndex = YamlUtils.loadFromFile(file, RepositoryIndex.class);

        return repositoryIndex;
    }

    public static void saveRepositoryIndex(String filePath, RepositoryIndex repository) {
        File file = new File(filePath);
        YamlUtils.saveToFile(file, repository, RepositoryIndex.class);
    }
}
