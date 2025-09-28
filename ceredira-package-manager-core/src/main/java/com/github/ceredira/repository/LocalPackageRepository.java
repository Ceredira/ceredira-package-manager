package com.github.ceredira.repository;

import com.github.ceredira.model.Package;

import java.util.List;
import java.util.Optional;

public class LocalPackageRepository implements PackageRepository {

    @Override
    public Optional<com.github.ceredira.model.Package> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<com.github.ceredira.model.Package> findAll() {
        return List.of();
    }

    @Override
    public List<Package> search(String query) {
        return List.of();
    }
}
