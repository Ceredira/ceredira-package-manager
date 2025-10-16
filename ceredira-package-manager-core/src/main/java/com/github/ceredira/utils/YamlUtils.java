package com.github.ceredira.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;

public class YamlUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
            .enable(YAMLGenerator.Feature.USE_PLATFORM_LINE_BREAKS))
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);;

    public static <T> T loadFromFile(File file, Class<T> clazz) {
        // Проверка входных параметров
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }

        // Проверка существования файла
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + file.getAbsolutePath());
        }

        // Проверка, что файл не является директорией
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File is actually a directory: " + file.getAbsolutePath());
        }

        // Проверка, что файл читаем
        if (!file.canRead()) {
            throw new IllegalArgumentException("File is not readable: " + file.getAbsolutePath());
        }

        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read configuration from file: " + file.getAbsolutePath(), e);
        }
    }

    public static <T> void saveToFile(File file, T object, Class<T> clazz) {
        // Проверка входных параметров
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }

        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }

        // Создание директории, если она не существует
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IllegalArgumentException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }

        // Настройка объекта маппера
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(
                JsonInclude.Include.NON_EMPTY,
                JsonInclude.Include.NON_NULL
        ));

        try {
            objectMapper.writeValue(file, object);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write configuration to file: " + file.getAbsolutePath(), e);
        }
    }
}
