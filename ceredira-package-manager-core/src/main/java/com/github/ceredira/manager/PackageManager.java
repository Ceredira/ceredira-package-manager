package com.github.ceredira.manager;

import com.github.ceredira.config.Config;
import com.github.ceredira.model.CpmPackage;
import com.github.ceredira.model.PackageFile;
import com.github.ceredira.model.PackageInfo;
import com.github.ceredira.repository.PackageRepository;
import com.github.ceredira.utils.SevenZUtils;
import com.github.ceredira.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import static com.github.ceredira.utils.Utils.getFullFilePath;
import static com.github.ceredira.utils.Utils.getUniqueDirectories;

@RequiredArgsConstructor
@Slf4j
public class PackageManager {

    public void init() {
        String initMsg = MessageFormat.format("Инициализация структуры Ceredira FHS в каталоге \"{0}\"...",
                Config.getRootPath().getPath());
        log.info(initMsg);

        Utils.init(Config.getRootPath());
    }

    public void install(String packageName, String versionName, String revisionName) {
        // ToDo:

        PackageInfo packageInfo = PackageRepository.getPackageInfo(packageName, versionName, revisionName);

        // ToDo: Проверить, что пакет существует

        PackageFile packageFilesArchive = packageInfo.getPackageFiles().stream()
                .filter(f -> f.getFileName().endsWith(".cpmf.7z"))
                .findFirst()
                .orElseThrow();

        PackageFile packageMetafilesArchive =  packageInfo.getPackageFiles().stream()
                 .filter(f -> f.getFileName().endsWith(".cpmm.7z"))
                 .findFirst()
                 .orElseThrow();

        try {
            File packageFilesArchiveFile = getFullFilePath(packageFilesArchive.getFileName());
            File packageMetafilesArchiveFile = getFullFilePath(packageMetafilesArchive.getFileName());

            SevenZUtils.decompress(packageFilesArchiveFile, Config.getRootPath());
            SevenZUtils.decompress(packageMetafilesArchiveFile, Config.getRootPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ToDo: Записать в файл installed информацию о том, что пакет установлен
    }

    public void uninstall(String packageName, String versionName, String revisionName) {
        PackageInfo packageInfo = PackageRepository.getPackageInfo(packageName, versionName, revisionName);

        // ToDo: Проверить, что пакет существует

        File rootPath = Config.getRootPath();

        for (String file : packageInfo.getFiles()) {
            File  fileToDelete = new File(rootPath, file);
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }

        for (String file : packageInfo.getMetaFiles()) {
            File  fileToDelete = new File(rootPath, file);
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }

        Set<String> folders = getUniqueDirectories(packageInfo.getFiles());

        for (String folder : folders) {
            File  folderToDelete = new File(rootPath, folder);
            if (folderToDelete.exists()) {
                folderToDelete.delete();
            }
        }

        // ToDo: Удалить из файла installed информацию о том, что пакет установлен
    }

    public void upgrade(String packageName) {
        throw new RuntimeException("Не реализовано");
    }

    public void upgradeAll() {
        throw new RuntimeException("Не реализовано");
    }

    public Set<String> list(String repositoryName, boolean installed) {
        return PackageRepository.getPackages(repositoryName);
    }

    public List<CpmPackage> search(String query) {
        throw new RuntimeException("Не реализовано");
    }

    public Set<String> info(String packageName) {
        String getPackageInfoMsg = MessageFormat.format("Получение информации по пакету {0}", packageName);
        log.info(getPackageInfoMsg);

        Set<String> packageVariations = PackageRepository.getPackageInfo(packageName);
        return packageVariations;
    }

    public List<CpmPackage> outdated() {
        throw new RuntimeException("Не реализовано");
    }

    // Дополнительные методы
    private void downloadAndExtract(CpmPackage pkg) {
        throw new RuntimeException("Не реализовано");
    }

    private void saveToLocal(CpmPackage pkg) {
        throw new RuntimeException("Не реализовано");
    }

    private boolean isInstalled(CpmPackage pkg) {
        throw new RuntimeException("Не реализовано");
    }
}
