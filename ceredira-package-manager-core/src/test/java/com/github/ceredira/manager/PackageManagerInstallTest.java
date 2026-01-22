package com.github.ceredira.manager;

import com.github.ceredira.BaseTest;
import com.github.ceredira.config.Config;
import com.github.ceredira.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.github.ceredira.utils.FileUtils.copyFile;

@Slf4j
class PackageManagerInstallTest extends BaseTest {

    @Test
    public void packageInstall() {
        File testRoot = Config.getRootPath();
        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destinationFile = new File(String.valueOf(testRoot), "var/cpm/origin/repository.yaml");

        FileUtils.createFileWithContent(destinationFile, repositoryOriginYaml);

        File repositoryOriginIndexYaml = new File("src/test/resources/index.yaml");
        File destinationIndexFile = new File(String.valueOf(testRoot), "var/cpm/origin/index.yaml");
        FileUtils.createFileWithContent(destinationIndexFile, repositoryOriginIndexYaml);

        File destinationPackageResFolder = new File(String.valueOf(testRoot),
                "var/cpm/origin/e/everything/1.4.1.1028");
        destinationPackageResFolder.mkdirs();
        copyFile(new File("src/test/resources/everything-1.4.1.1028-r1.cpmf.7z"),
                new File(destinationPackageResFolder, "everything-1.4.1.1028-r1.cpmf.7z"));
        copyFile(new File("src/test/resources/everything-1.4.1.1028-r1.cpmm.7z"),
                new File(destinationPackageResFolder, "everything-1.4.1.1028-r1.cpmm.7z"));
        copyFile(new File("src/test/resources/everything-1.4.1.1028-r1.yaml"),
                new File(destinationPackageResFolder, "everything-1.4.1.1028-r1.yaml"));

        pm = new PackageManager();
        pm.install("everything", "1.4.1.1028", "r1");
        log.info("Пакет установлен");
    }
}