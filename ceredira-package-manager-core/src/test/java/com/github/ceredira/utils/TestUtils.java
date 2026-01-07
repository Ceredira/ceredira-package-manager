package com.github.ceredira.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    // Статическое поле гарантирует, что время зафиксируется один раз при первом обращении
    private static final String RUN_TIME = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    private static final Path BASE_TEST_DIR = Path.of("target", "tests", RUN_TIME);

    public static Path getRunRootPath() {
        try {
            return Files.createDirectories(BASE_TEST_DIR);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test root directory", e);
        }
    }

    public static Path getClassPath(Class<?> clazz) {
        try {
            return Files.createDirectories(getRunRootPath().resolve(clazz.getSimpleName()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create class test directory", e);
        }
    }

}
