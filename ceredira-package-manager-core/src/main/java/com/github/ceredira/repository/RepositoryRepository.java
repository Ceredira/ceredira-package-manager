package com.github.ceredira.repository;

import com.github.ceredira.model.Repository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryRepository {
    private static final Map<String, Repository> repositories = new HashMap<>();

    static {

    }

    public static Repository getRepository(String repository) {
        return repositories.get(repository);
    }

    public static void addRepository(String name, Repository repository) {
        repositories.put(name, repository);
    }
}
