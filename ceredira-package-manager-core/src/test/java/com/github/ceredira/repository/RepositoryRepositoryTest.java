package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.config.Config;
import com.github.ceredira.manager.PackageManager;
import com.github.ceredira.model.Repository;
import com.github.ceredira.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class RepositoryRepositoryTest extends BaseTest {

    @Test
    void getRepository() {
        Path rootPath = Config.getRootPathAsPath();

        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destinationFile = new File(String.valueOf(rootPath), "var/cpm/origin/repository.yaml");
        FileUtils.createFileWithContent(destinationFile, repositoryOriginYaml);

        Repository origin = RepositoryRepository.getRepository("origin");
        assertNotNull(origin);

        log.info(origin.toString());
    }
}