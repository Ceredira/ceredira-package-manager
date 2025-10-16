package com.github.ceredira.repository;

import com.github.ceredira.manager.PackageManager;
import com.github.ceredira.model.Repository;
import com.github.ceredira.utils.FileUtils;
import com.github.ceredira.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class RepositoryRepositoryTest {

    private static final File rootFolder = TestUtils.getTestFolder();

    @BeforeAll
    public static void setUp() {
        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destFolder = new File(rootFolder, "var/cpm/origin/repository.yaml");
        FileUtils.createFileWithContent(destFolder, repositoryOriginYaml);
    }

    @Test
    void getRepository() {
        Repository origin = RepositoryRepository.getRepository("origin");
        assertNotNull(origin);

        log.info(origin.toString());
    }
}