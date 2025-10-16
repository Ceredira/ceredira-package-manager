package com.github.ceredira.repository;

import com.github.ceredira.config.Config;
import com.github.ceredira.model.PackageInfo;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PackageRepository {
    @Getter
    private static final Map<String, Map<String, PackageInfo>> packages = new HashMap<>();

    static {
        File root = Config.getFileFromRoot("var/cpm");
        for (File file : root.listFiles(File::isDirectory)) {
            File repositoryYaml = new File(file, "repository.yaml");
            if (repositoryYaml.exists()) {
                // Repository repository = RepositoryUtils.loadRepository(repositoryYaml.getPath());
                // packages.put(repository.getName(), repository);
            }
        }
    }

    public static Map<String, PackageInfo> getPackages(String repositoryName) {
        return packages.get(repositoryName);
    }

    public static PackageInfo getPackage(String repositoryName, String packageName) {
        return packages.get(repositoryName).get(packageName);
    }

    public static void addPackage(String repositoryName, String packageName, PackageInfo packageInfo) {
        packages.computeIfAbsent(repositoryName, k -> new HashMap<>()).put(packageName, packageInfo);
    }
}
