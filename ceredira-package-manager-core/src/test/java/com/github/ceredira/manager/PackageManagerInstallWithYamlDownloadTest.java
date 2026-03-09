package com.github.ceredira.manager;

import com.github.ceredira.BaseTest;
import com.github.ceredira.config.Config;
import com.github.ceredira.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

@Slf4j
class PackageManagerInstallWithYamlDownloadTest extends BaseTest {

    @Test
    public void packageInstallWithYamlDownloadTest() {
        File testRoot = Config.getRootPath();
        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destinationFile = new File(String.valueOf(testRoot), "var/cpm/origin/repository.yaml");

        FileUtils.createFileWithContent(destinationFile, repositoryOriginYaml);

        File repositoryOriginIndexYaml = new File("src/test/resources/index.yaml");
        File destinationIndexFile = new File(String.valueOf(testRoot), "var/cpm/origin/index.yaml");
        FileUtils.createFileWithContent(destinationIndexFile, repositoryOriginIndexYaml);

        pm.install("everything", "1.4.1.1028", "r1");
        log.info("Пакет установлен");
    }
}