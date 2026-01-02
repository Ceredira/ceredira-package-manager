package com.github.ceredira.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Comparator;

@Slf4j
public class FolderUtils {
    public static void createFolder(File parentFolder, String folderName) {
        File folder = new File(parentFolder, folderName);
        String folderAbsolutePath = folder.getAbsolutePath();

        String folderCreating = MessageFormat.format("Создание каталога: \"{0}\"", folderAbsolutePath);
        log.info(folderCreating);

        createFolder(folder);
    }

    public static void createFolder(File folder) {
        String folderAbsolutePath = folder.getAbsolutePath();

        if (folder.exists()) {
            String folderAlreadyExists = MessageFormat.format("Каталог \"{0}\" уже существует", folderAbsolutePath);
            log.info(folderAlreadyExists);
        } else {
            boolean created = folder.mkdir();

            if (created) {
                String folderCreated = MessageFormat.format("Каталог \"{0}\" создан", folderAbsolutePath);
                log.info(folderCreated);
            } else {
                String folderCreationError = MessageFormat.format("Не удалось создать каталог \"{0}\"", folderAbsolutePath);
                log.error(folderCreationError);
                throw new RuntimeException(folderCreationError);
            }
        }
    }

    public static void deleteFolderWithContent(Path path) {
        try (var stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder()) // Сначала содержимое, потом папка
                    .forEach(subpath -> {
                        try {
                            Files.delete(subpath);
                        } catch (IOException e) {
                            String msg = String.format("Ошибка при удалении каталога \"%s\": %s", subpath, e.getMessage());
                            log.error(msg);
                            throw new RuntimeException(msg, e);
                        }
                    });

            log.debug("Каталог \"{}\" успешно удален", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
