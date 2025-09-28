package com.github.ceredira.utils;

import com.github.ceredira.model.Package;
import com.github.ceredira.repository.LocalPackageRepository;

import java.util.List;

public class DependencyResolver {
    public List<com.github.ceredira.model.Package> resolveDependencies(Package pkg, LocalPackageRepository localRepo) {
        throw new RuntimeException("Не реализовано");
    }
}
