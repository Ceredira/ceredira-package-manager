package com.github.ceredira.utils;

import com.github.ceredira.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RepositoryManagerTest {

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void loadRepositories() {
        List<Repository> repositories = RepositoryManager.loadRepositories("src/test/resources/repositories.yaml");
        assertNotNull(repositories);

        log.info(repositories.toString());
    }

    @Test
    void saveRepositories() {
        List<Repository> repositories = RepositoryManager.loadRepositories("src/test/resources/repositories.yaml");
        repositories.getFirst().setEnabled(false);

        RepositoryManager.saveRepositories("target/repositories.yaml", repositories);
    }
}