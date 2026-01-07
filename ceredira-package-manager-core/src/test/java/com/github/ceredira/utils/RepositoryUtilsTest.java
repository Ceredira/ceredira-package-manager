package com.github.ceredira.utils;

import com.github.ceredira.BaseTest;
import com.github.ceredira.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class RepositoryUtilsTest extends BaseTest {

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void loadRepositories() {
        Repository repository = RepositoryUtils.loadRepository("src/test/resources/repositoryOrigin.yaml");
        assertNotNull(repository);

        log.info(repository.toString());
    }

    @Test
    void saveRepositories() {
        Repository repository = RepositoryUtils.loadRepository("src/test/resources/repositoryOrigin.yaml");
        repository.setEnabled(false);

        RepositoryUtils.saveRepository("target/repositoryOrigin.yaml", repository);
    }
}