package com.github.ceredira.manager;

import com.github.ceredira.model.Package;
import com.github.ceredira.repository.LocalPackageRepository;
import com.github.ceredira.repository.RemotePackageRepository;
import com.github.ceredira.utils.DependencyResolver;
import com.github.ceredira.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
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

    public List<com.github.ceredira.model.Package> list(boolean localOnly) {
        throw new RuntimeException("Не реализовано");
    }

    public List<com.github.ceredira.model.Package> search(String query) {
        throw new RuntimeException("Не реализовано");
    }

    public com.github.ceredira.model.Package info(String packageName) {
        throw new RuntimeException("Не реализовано");
    }

    public List<com.github.ceredira.model.Package> outdated() {
        throw new RuntimeException("Не реализовано");
    }

    // Дополнительные методы
    private void downloadAndExtract(com.github.ceredira.model.Package pkg) {
        throw new RuntimeException("Не реализовано");
    }

    private void saveToLocal(com.github.ceredira.model.Package pkg) {
        throw new RuntimeException("Не реализовано");
    }

    private boolean isInstalled(Package pkg) {
        throw new RuntimeException("Не реализовано");
    }
}