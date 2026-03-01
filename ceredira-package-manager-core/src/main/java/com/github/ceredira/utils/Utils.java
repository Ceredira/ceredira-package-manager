package com.github.ceredira.utils;

import com.github.ceredira.config.Config;
import com.github.ceredira.manager.RepositoryManager;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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

    // ToDo: getFullFilePathForRemoteRepository(String Repository, String packageName) - взять origin
    public static String getRemoteFullFilePath(String repositoryName, String packageName, String packageVersion, String packageFileName) {

        String packageNameFirstChar = String.valueOf(packageName.charAt(0)); // e

        String repositoryUrl = new RepositoryManager().getRepositories().get(repositoryName).getUrl().toString();

        return String.format("%s/%s/%s/%s/%s",
                repositoryUrl,
                packageNameFirstChar,
                packageName,
                packageVersion,
                packageFileName
        );
    }

    public static File getLocalFullFilePath(String repositoryName, String packageName, String packageVersion, String packageFileName) {
        String firstChar = String.valueOf(packageName.charAt(0)); // e

        return new File(Config.getFileFromRoot("/var/cpm"),
                String.format("%s/%s/%s/%s/%s", repositoryName, firstChar, packageName, packageVersion, packageFileName));
    }

    public static Set<String> getUniqueDirectories(Collection<String> filePaths) {
        return filePaths.stream()
                .map(Paths::get)
                .map(Path::getParent)
                .filter(parent -> parent != null)
                .map(Path::toString)
                .collect(Collectors.toSet());
    }
}
