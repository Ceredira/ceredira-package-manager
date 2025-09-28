package com.github.ceredira.repository;

import com.github.ceredira.model.CpmPackage;

import java.util.List;
import java.util.Optional;

public interface PackageRepository {
    Optional<CpmPackage> findByName(String name);
    List<CpmPackage> findAll();
    List<CpmPackage> search(String query);
}
