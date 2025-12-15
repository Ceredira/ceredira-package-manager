package com.github.ceredira.manager;

import com.github.ceredira.model.PackageInfo;
import com.github.ceredira.utils.FileUtils;
import com.github.ceredira.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

@Slf4j
class PackageManagerInfoTest {

    private static final String rootFolder = TestUtils.getTestFolder().getAbsolutePath();

    @BeforeAll
    public static void setUp() {
        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destinationFile = new File(rootFolder, "var/cpm/origin/repository.yaml");
        FileUtils.createFileWithContent(destinationFile, repositoryOriginYaml);

        File repositoryOriginIndexYaml = new File("src/test/resources/index.yaml");
        File destinationIndexFile = new File(rootFolder, "var/cpm/origin/index.yaml");
        FileUtils.createFileWithContent(destinationIndexFile, repositoryOriginIndexYaml);
    }

    @Test
    public void getPackageInfo() {
        PackageManager pm = new PackageManager();

        Set<String> packageVariations = pm.info("everything");

        log.info("Версии пакетов:");
        for (String s : packageVariations) {
            log.info("    {}", s);
        }
    }
}