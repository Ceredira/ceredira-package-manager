package com.github.ceredira.repository;

import com.github.ceredira.config.Config;
import com.github.ceredira.model.Repository;
import com.github.ceredira.utils.RepositoryUtils;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RepositoryRepository {
    @Getter
    private static final Map<String, Repository> repositories = new HashMap<>();

    static {
        File root = Config.getFileFromRoot("var/cpm");
        for (File file : root.listFiles(File::isDirectory)) {
            File repositoryYaml = new File(file, "repository.yaml");
            if (repositoryYaml.exists()) {
                Repository repository = RepositoryUtils.loadRepository(repositoryYaml.getPath());
                repositories.put(repository.getName(), repository);
            }
        }
    }

    public static Repository getRepository(String repository) {
        return repositories.get(repository);
    }

    public static void addRepository(String name, Repository repository) {
        repositories.put(name, repository);
    }
}
