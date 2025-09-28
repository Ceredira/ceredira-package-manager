package com.github.ceredira.utils;

import com.github.ceredira.model.CpmPackage;
import com.github.ceredira.repository.LocalPackageRepository;

import java.util.List;

public class DependencyResolver {
    public List<CpmPackage> resolveDependencies(CpmPackage pkg, LocalPackageRepository localRepo) {
        throw new RuntimeException("Не реализовано");
    }
}
