package com.github.ceredira.repository;

import com.github.ceredira.config.Config;
import com.github.ceredira.model.*;
import com.github.ceredira.utils.PackageInfoUtils;
import com.github.ceredira.utils.RepositoryIndexUtils;
import com.github.ceredira.utils.YamlUtils;
import lombok.Getter;

import java.io.File;
import java.util.*;

public class PackageRepository {
    @Getter
    private static final Map<String, Map<String, PackageInfo>> packages = new HashMap<>();

    @Getter
    private static final Map<String, RepositoryIndex> indexes = new HashMap<>();

    @Getter
    private static Map<String, Set<String>> installed = new HashMap<>();

    @Getter
    private static final File repositoryRoot = Config.getFileFromRoot("var/cpm");

    @Getter
    private static File repositoryInstalledYaml = new File(repositoryRoot, "installed.yaml");

    static {
        for (Repository r : RepositoryRepository.getRepositories().values()) {

            File repositoryIndexYaml = new File(repositoryRoot, r.getName() + "/index.yaml");
            if (repositoryIndexYaml.exists()) {
                RepositoryIndex repositoryIndex = RepositoryIndexUtils.getRepositoryIndex(repositoryIndexYaml.getPath());
                indexes.put(r.getName(), repositoryIndex);
            }
        }

        if (repositoryInstalledYaml.exists()) {
            installed = (Map<String, Set<String>>) YamlUtils.loadFromFile(repositoryInstalledYaml, Map.class);
        }
    }

    public static Set<String> getPackages(String repositoryName) {
        Map<String, RepositoryPackage> packages = indexes.get(repositoryName).getPackages();

        return packages.keySet();
    }

    public static Set<String> getPackageVersions(String repositoryName, String packageName) {
        Map<String, PackageVersion> versions = indexes.get(repositoryName).getPackages().get(packageName).getVersions();

        return versions.keySet();
    }

    public static Set<String> getPackageRevisions(String repositoryName, String packageName, String version) {
        Map<String, PackageRevision> revisions = indexes.get(repositoryName).getPackages().get(packageName)
                .getVersions().get(version)
                .getRevisions();

        return revisions.keySet();
    }

    // ToDo: изменить возвращаемый тип, возвращать мапу с именем репозитория и пакетами из него
    public static Set<String> getPackageInfo(String packageName) {
        // ToDo: сделать поиск по всем репозиториям
        String repositoryName = "origin";

        // ToDo: добавить проверку наличия репозитория и наличия пакета в нем
        RepositoryPackage repositoryPackage = indexes.get(repositoryName).getPackages().get(packageName);

        Set<String> packageNames =  new HashSet<>();


        Map<String, PackageVersion> versions = repositoryPackage.getVersions();
        for (Map.Entry<String, PackageVersion> version : versions.entrySet()) {
            String versionName = version.getKey();

            Map<String, PackageRevision> revisions = version.getValue().getRevisions();
            for (String revisionName : revisions.keySet()) {
                String fullPackageName = String.format("%s-%s-%s",
                        packageName,
                        versionName,
                        revisionName);
                packageNames.add(fullPackageName);
            }
        }

        return packageNames;
    }

    public static PackageInfo getPackageInfo(String packageName, String version, String revision) {
        // ToDo: сделать поиск по всем репозиториям
        String repositoryName = "origin";

        // ToDo: проверить потом контрольную сумму
        String sha26 = indexes.get(repositoryName).getPackages().get(packageName)
                .getVersions().get(version)
                .getRevisions().get(revision).getSha256();

        File root = Config.getFileFromRoot("var/cpm");

        String fileName = String.format("%1$s/%2$s/%3$s/%4$s/%5$s/%4$s-%5$s-%6$s.yaml",
                repositoryRoot,
                repositoryName,
                packageName.charAt(0),
                packageName,
                version,
                revision);

        PackageInfo packageInfo = PackageInfoUtils.getPackageInfo(fileName);

        return packageInfo;
    }

    public static void markPackageAsInstalled(String packageName, String versionName, String revisionName) {
        if (!installed.containsKey("origin"))
            installed.put("origin", new HashSet<>());

        String fullPackageName = String.format("%s-%s-%s", packageName, versionName, revisionName);
        installed.get("origin").add(fullPackageName);

        YamlUtils.saveToFile(repositoryInstalledYaml, installed, Map.class);
    }

    public static void unmarkPackageAsInstalled(String packageName, String versionName, String revisionName) {
        if (!installed.containsKey("origin"))
            installed.put("origin", new HashSet<>());

        String fullPackageName = String.format("%s-%s-%s", packageName, versionName, revisionName);
        installed.get("origin").remove(fullPackageName);

        YamlUtils.saveToFile(repositoryInstalledYaml, installed, Map.class);
    }

    public static boolean isPackageInstalled(String packageName, String versionName, String revisionName) {
        if (!installed.containsKey("origin"))
            installed.put("origin", new HashSet<>());

        String fullPackageName = String.format("%s-%s-%s", packageName, versionName, revisionName);
        return installed.get("origin").contains(fullPackageName);
    }
}
