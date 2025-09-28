package com.github.ceredira.repository;

import com.github.ceredira.model.CpmPackage;

import java.util.List;
import java.util.Optional;

public class RemotePackageRepository implements PackageRepository {
    @Override
    public Optional<CpmPackage> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<CpmPackage> findAll() {
        return List.of();
    }

    @Override
    public List<CpmPackage> search(String query) {
        return List.of();
    }
    // реализация работы с удалёнными пакетами
}
