package com.github.ceredira.manager;

import com.github.ceredira.model.CpmPackage;
import com.github.ceredira.model.PackageInfo;
import com.github.ceredira.repository.LocalPackageRepository;
import com.github.ceredira.repository.RemotePackageRepository;
import com.github.ceredira.utils.DependencyResolver;
import com.github.ceredira.utils.PackageMetadataLoader;
import com.github.ceredira.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class PackageManager {
    private String rootFolder;
    private File rootFolderFile;

    private final LocalPackageRepository localRepo = null;
    private final RemotePackageRepository remoteRepo = null;
    private final DependencyResolver dependencyResolver = null;

    public PackageManager(String rootFolder) {
        this.rootFolder = rootFolder;
        this.rootFolderFile = new File(rootFolder);
    }

    public void init() {
        String initMsg = MessageFormat.format("Инициализация структуры Ceredira FHS в каталоге \"{0}\"...",
                rootFolderFile.getAbsolutePath());
        log.info(initMsg);

        Utils.init(rootFolderFile);
    }

    public void install(String packageName) {
        throw new RuntimeException("Не реализовано");
    }

    public void uninstall(String packageName) {
        throw new RuntimeException("Не реализовано");
    }

    public void upgrade(String packageName) {
        throw new RuntimeException("Не реализовано");
    }

    public void upgradeAll() {
        throw new RuntimeException("Не реализовано");
    }

    public List<CpmPackage> list(boolean localOnly) {
        throw new RuntimeException("Не реализовано");
    }

    public List<CpmPackage> search(String query) {
        throw new RuntimeException("Не реализовано");
    }

    public PackageInfo info(String packageName) {
        String getPackageInfoMsg = MessageFormat.format("Получение информации по пакету {0}", packageName);
        log.info(getPackageInfoMsg);

        PackageInfo packageInfo = PackageMetadataLoader.getPackageInfo(packageName);
        return packageInfo;
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