package com.github.ceredira;

import com.github.ceredira.config.Config;
import com.github.ceredira.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseTest {
    protected static Path classRootPath;
    @BeforeAll
    static void globalTestSetUp(TestInfo testInfo) throws IOException {

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        Path targetDir = Path.of("target");
        Path testsDir = targetDir.resolve("tests");
        Files.createDirectories(testsDir); // createDirectories безопасно: не падает, если уже существует

        Path dateDir = testsDir.resolve(currentTime);
        Files.createDirectories(dateDir);

        String className = testInfo.getTestClass()
                .map(Class::getSimpleName)
                .orElse("unknown-test");

        classRootPath = dateDir.resolve(className);
        Files.createDirectories(classRootPath);

        Config.setRootPath(dateDir.toFile());
    }

    @BeforeEach
    void setupClassName() {

        // Получаем путь для текущего класса
        classRootPath = TestUtils.getClassPath(this.getClass());

        // Синхронизируем глобальный конфиг
        Config.setRootPath(classRootPath.toFile());
    }
}
