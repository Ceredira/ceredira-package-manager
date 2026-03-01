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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import static com.github.ceredira.utils.NetUtils.downloadFile;
import static com.github.ceredira.utils.Utils.*;

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
        String repositoryName = "origin";
        PackageInfo packageInfo = searchPackageInfo(packageName, versionName, revisionName);

        // Если архивы пакета не существуют, то скачиваем
        download(packageInfo);

        packageFilesAction(packageInfo, (file, url) -> {
            try {
                SevenZUtils.decompress(file, Config.getRootPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        List<PackageFile> packageFiles = getPackageFiles(packageInfo);

        for (PackageFile packageFile: packageFiles){
            File packageFilesArchive = getLocalFullFilePath(
                    repositoryName,
                    packageName,
                    versionName,
                    packageFile.getFileName()
            );

            try {
                SevenZUtils.decompress(packageFilesArchive, Config.getRootPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Записать в файл installed информацию о том, что пакет установлен
        PackageRepository.markPackageAsInstalled(packageName, versionName, revisionName);
    }

    public void uninstall(String packageName, String versionName, String revisionName) {
        PackageInfo packageInfo = PackageRepository.getPackageInfo(packageName, versionName, revisionName);

        // ToDo: Проверить, что пакет существует
        if (!PackageRepository.isPackageInstalled(packageName, versionName, revisionName)) {
            String fullPackageName = String.format("%s-%s-s", packageName, versionName, revisionName);
            throw new RuntimeException("Указанного пакета не существует: " + fullPackageName);
        }

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
        PackageRepository.unmarkPackageAsInstalled(packageName, versionName, revisionName);
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
    private void download(String packageName, String versionName, String revisionName) {
        String repositoryName = "origin";
        PackageInfo packageInfo = searchPackageInfo(packageName, versionName, revisionName);
        download(packageInfo);
    }

    private void download(PackageInfo packageInfo) {
        String repositoryName = "origin";
        List<PackageFile> packageFiles = getPackageFiles(packageInfo);

        packageFilesAction(packageInfo, (file, url) -> {
            if (!file.exists()) {
                try {
                    downloadFile(url, file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private boolean isInstalled(CpmPackage pkg) {
        throw new RuntimeException("Не реализовано");
    }

    private record DownloadTask(PackageFile filesArchive, File filesPath,
                                PackageFile metaArchive, File metaPath) {
    }

    private PackageInfo searchPackageInfo(String packageName, String versionName, String revisionName) {
        String repositoryName = "origin";
        Set<String> packages = PackageRepository.getPackages(repositoryName);

        boolean found = packages.contains(packageName);

        if (!found) {
            throw new RuntimeException("Значение не найдено!");
        }

        PackageInfo packageInfo = PackageRepository.getPackageInfo(packageName, versionName, revisionName);
        return packageInfo;
    }

    private List<PackageFile> getPackageFiles(PackageInfo packageInfo) {
        List<PackageFile> packageFiles = new ArrayList<>();
        for (PackageFile packageFile : packageInfo.getPackageFiles()) {
            if (packageFile.getFileName().endsWith(".cpmf.7z") || packageFile.getFileName().endsWith(".cpmm.7z")) {
                packageFiles.add(packageFile);
            }
        }

        return packageFiles;
    }

    public void packageFilesAction(PackageInfo packageInfo, BiConsumer<File, String> action) {
        List<PackageFile> packageFiles = getPackageFiles(packageInfo);

        for (PackageFile packageFile: packageFiles) {
            File packageFilesArchive = getLocalFullFilePath(
                    "origin",
                    packageInfo.getCpmPackage().getName(),
                    packageInfo.getOriginalPackage().getVersion(),
                    packageFile.getFileName()
            );

            String packageFilesArchiveFileUrl = getRemoteFullFilePath(
                    "origin",
                    packageInfo.getCpmPackage().getName(),
                    packageInfo.getOriginalPackage().getVersion(),
                    packageFile.getFileName()
            );

            action.accept(packageFilesArchive, packageFilesArchiveFileUrl);
        }
    }
}
