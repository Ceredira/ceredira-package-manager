package com.github.ceredira.utils;

import com.github.ceredira.model.RepositoriesConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RepositoryUtilsTest {

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void loadRepositories() {
        RepositoriesConfig repositoriesConfig = RepositoryUtils.loadRepositories("src/test/resources/repositories.yaml");
        assertNotNull(repositoriesConfig);
        assertNotNull(repositoriesConfig.getRepositories());
        assertFalse(repositoriesConfig.getRepositories().isEmpty());

        log.info(repositoriesConfig.toString());
    }

    @Test
    void saveRepositories() {
        RepositoriesConfig repositoriesConfig = RepositoryUtils.loadRepositories("src/test/resources/repositories.yaml");
        repositoriesConfig.getRepositories().get("origin").setEnabled(false);

        RepositoryUtils.saveRepositories("target/repositories.yaml", repositoriesConfig);
    }
}