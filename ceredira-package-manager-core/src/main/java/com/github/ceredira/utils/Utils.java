package com.github.ceredira.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;

@Slf4j
public class Utils {

    public static void init(File rootFolder) {
        // Массив с названиями каталогов
        String[] foldersNames = {"bin", "etc", "home", "lib", "log", "man", "opt", "tmp", "var", "var/cpm", "var/cpm/origin"};

        String[] files = {".gitignore", "README.md"};


        String folderNamesString = MessageFormat.format("Массив с названиями каталогов: {0}", Arrays.toString(foldersNames));
        log.info(folderNamesString);

        log.info("Создание каталогов...");
        for (String folderName : foldersNames) {
            FolderUtils.createFolder(rootFolder, folderName);
        }

        String fileNamesString = MessageFormat.format("Массив с названиями файлов: {0}", Arrays.toString(files));
        log.info(fileNamesString);

        log.info("Создание файлов...");
        for (String fileName : files) {
            FileUtils.createFile(rootFolder, fileName);
        }
    }
}
