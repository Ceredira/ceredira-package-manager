package com.github.ceredira.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;

@Slf4j
public class FileUtils {
    public static void createFileWithContent(File parentFolder, String fileName, File content) {
        File file = new File(parentFolder, fileName);
        String fileAbsolutePath = file.getAbsolutePath();

        String fileCreating = MessageFormat.format("Создание файла: \"{0}\"", fileAbsolutePath);
        log.info(fileCreating);

        createFileWithContent(file, content);
    }

    public static void createFileWithContent(File file, File content) {
        String fileAbsolutePath = file.getAbsolutePath();

        createFile(file);


        String contentAbsolutePath = content.getAbsolutePath();

        // Заполнение файла содержимым
        if (content.isFile() && content.exists()) {
            Path source = Paths.get(content.getAbsolutePath());
            Path target = Paths.get(file.getAbsolutePath());

            try {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

                String fileFillWithContent = MessageFormat.format(
                        "Файл \"{0}\" заполнен содержимым из файла: \"{1}\"",
                        fileAbsolutePath,
                        contentAbsolutePath);

                log.info(fileFillWithContent);
            } catch (IOException e) {
                String contentCopyError = MessageFormat.format("Файл \"{0}\" не удалось скопировать в \"{1}\"",
                        contentAbsolutePath,
                        fileAbsolutePath);

                log.error(contentCopyError);
                throw new RuntimeException(contentCopyError);
            }
        } else {
            String contentFileReadError = MessageFormat.format(
                    "Файл \"{0}\" не является файлом или не существует",
                    contentAbsolutePath);

            log.error(contentFileReadError);
            throw new RuntimeException(contentFileReadError);
        }
    }

    public static void createFile(File parentFolder, String fileName) {
        File file = new File(parentFolder, fileName);
        String fileAbsolutePath = file.getAbsolutePath();

        String fileCreating = MessageFormat.format("Создание файла: \"{0}\"", fileAbsolutePath);
        log.info(fileCreating);

        createFile(file);
    }

    public static void createFile(File file) {
        String fileAbsolutePath = file.getAbsolutePath();

        try {
            if (file.exists()) {
                String fileAlreadyExists = MessageFormat.format("Файл \"{0}\" уже существует",
                        fileAbsolutePath);

                log.info(fileAlreadyExists);
            } else {
                boolean created = file.createNewFile();

                if (created) {
                    String fileCreated = MessageFormat.format("Файл \"{0}\" создан", fileAbsolutePath);
                    log.info(fileCreated);
                } else {
                    String fileCreationError = MessageFormat.format("Не удалось создать файл \"{0}\"",
                            fileAbsolutePath);

                    log.error(fileCreationError);
                    throw new RuntimeException(fileCreationError);
                }
            }
        } catch (IOException e) {
            String fileCreationError = MessageFormat.format("Ошибка при создании файла \"{0}\": {1}",
                    fileAbsolutePath,
                    e.getMessage());

            log.error(fileCreationError);
            throw new RuntimeException(fileCreationError);
        }
    }

    public static String getFileContentAsString(File destination) {
        String result;
        result = getFileContentAsString(destination, StandardCharsets.UTF_8);
        return result;
    }

    public static String getFileContentAsString(File destination, Charset cs) {
        String result;
        try {
            result = Files.readString(destination.toPath(), cs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void copyFile(File source, File destination) {
         try {
              Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
         } catch (IOException e) {
              throw new RuntimeException(e);
         }
    }
}
