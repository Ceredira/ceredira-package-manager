package com.github.ceredira.manager;

import com.github.ceredira.BaseTest;
import com.github.ceredira.config.Config;
import com.github.ceredira.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

@Slf4j
class PackageManagerInstallWithDownloadsTest extends BaseTest {

    @Test
    public void packageInstallWithYamlDownloadTest() {
        File testRoot = Config.getRootPath();
        PackageManager pm = new PackageManager();

        pm.init();

        RepositoryManager rm = new RepositoryManager();
        rm.addRepository("origin", "http://localhost:80/");
        rm.getRepositories().get("origin").setEnabled(true);

        pm.install("everything", "1.4.1.1028", "r1");
        log.info("Пакет установлен");
    }
}