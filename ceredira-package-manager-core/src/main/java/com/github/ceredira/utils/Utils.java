package com.github.ceredira.utils;

import com.github.ceredira.config.Config;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
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

    public static File getFullFilePath(String packageName) {
        // Регулярное выражение:
        // ^([^-]+)  - захватывает все до первого дефиса (название: everything)
        // -([\d.]+) - захватывает дефис и следующую за ним версию из цифр и точек (версия: 1.4.1.1028)
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^([^-]+)-([\\d.]+)");
        java.util.regex.Matcher matcher = pattern.matcher(packageName);

        if (matcher.find()) {
            String name = matcher.group(1);    // everything
            String version = matcher.group(2); // 1.4.1.1028
            String firstChar = String.valueOf(name.charAt(0)); // e

            return new File(Config.getFileFromRoot("/var/cpm/origin"),
                    String.format("%s/%s/%s/%s", firstChar, name, version, packageName));
        }

        throw new RuntimeException("Не удалось распарсить имя пакета");
    }
}
