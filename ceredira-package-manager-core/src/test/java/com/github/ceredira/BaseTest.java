package com.github.ceredira;

import com.github.ceredira.config.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public abstract class BaseTest {
    protected static Path classRootPath;
    private static final String RUN_TIMESTAMP =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    public static String urlExample = "http://ceredira.org/index.yaml";

    @BeforeAll
    static void globalTestSetUp(TestInfo testInfo) throws IOException {

        Path targetDir = Path.of("target");
        Path testsDir = targetDir.resolve("tests");
        Path dateDir = testsDir.resolve(RUN_TIMESTAMP);

        String className = testInfo.getTestClass()
                .map(Class::getSimpleName)
                .orElse("UnknownTest");

        classRootPath = dateDir.resolve(className);
        Files.createDirectories(classRootPath); // создаём папку класса
    }

    @BeforeEach
    void setupClassName(TestInfo testInfo) throws IOException {

        // Получаем имя тестового метода
        String methodName = testInfo.getTestMethod()
                .map(Method::getName)
                .orElse("unknown_test");

        // Создаём папку для текущего теста: .../MyTestClass/testMethod
        Path testPath = classRootPath.resolve(methodName);
        Files.createDirectories(testPath);

        // Устанавливаем её как корень для Config
        Config.setRootPath(testPath.toFile());
    }

    // тестовые данные для пустого имени репозитория
    static Stream<Arguments> provideEmptyOrBlankNames() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("\t\n"),
                Arguments.of(" \r\n  ")
        );
    }
}
