package com.github.ceredira.manager;

import com.github.ceredira.model.CpmPackage;
import com.github.ceredira.model.PackageInfo;
import com.github.ceredira.utils.PackageInfoUtils;
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

        PackageInfo packageInfo = PackageInfoUtils.getPackageInfo(packageName);
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
