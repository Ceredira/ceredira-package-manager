package com.github.ceredira.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;

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
}
