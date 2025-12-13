package com.github.ceredira.repository;

import com.github.ceredira.config.Config;
import com.github.ceredira.model.*;
import com.github.ceredira.utils.RepositoryIndexUtils;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PackageRepository {
    @Getter
    private static final Map<String, Map<String, PackageInfo>> packages = new HashMap<>();

    @Getter
    private static final Map<String, RepositoryIndex> indexes = new HashMap<>();

    static {
        for (Repository r : RepositoryRepository.getRepositories().values()) {
            File root = Config.getFileFromRoot("var/cpm/" + r.getName());

            File repositoryIndexYaml = new File(root, "index.yaml");
            if (repositoryIndexYaml.exists()) {
                RepositoryIndex repositoryIndex = RepositoryIndexUtils.getRepositoryIndex(repositoryIndexYaml.getPath());
                indexes.put(r.getName(), repositoryIndex);
            }
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

    public static PackageInfo getPackage(String repositoryName, String packageName, String version, String revision) {
        String sha26 = indexes.get(repositoryName).getPackages().get(packageName)
                .getVersions().get(version)
                .getRevisions().get(revision).getSha26();

        File root = Config.getFileFromRoot("var/cpm");

        String fileName = String.format("{0}/{1}/{2}/{3}/{2}-{3}-{4}.yaml",
                root,
                repositoryName,
                packageName,
                version,
                revision);

        return packages.get(repositoryName).get(packageName);
    }
}
