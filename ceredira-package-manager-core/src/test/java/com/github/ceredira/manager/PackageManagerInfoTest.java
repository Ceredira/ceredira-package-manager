package com.github.ceredira.manager;

import com.github.ceredira.BaseTest;
import com.github.ceredira.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

@Slf4j
class PackageManagerInfoTest extends BaseTest {

    @Test
    public void getPackageInfo() {
        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destinationFile = new File(String.valueOf(classRootPath), "var/cpm/origin/repository.yaml");
        FileUtils.createFileWithContent(destinationFile, repositoryOriginYaml);

        File repositoryOriginIndexYaml = new File("src/test/resources/index.yaml");
        File destinationIndexFile = new File(String.valueOf(classRootPath), "var/cpm/origin/index.yaml");
        FileUtils.createFileWithContent(destinationIndexFile, repositoryOriginIndexYaml);

        Set<String> packageVariations = pm.info("everything");

        log.info("Версии пакетов:");
        for (String s : packageVariations) {
            log.info("    {}", s);
        }
    }
}