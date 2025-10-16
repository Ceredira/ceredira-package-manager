package com.github.ceredira.repository;

import com.github.ceredira.model.PackageInfo;

import java.util.HashMap;
import java.util.Map;

public class PackageRepository {
    private static final Map<String, Map<String, PackageInfo>> packages = new HashMap<>();

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
