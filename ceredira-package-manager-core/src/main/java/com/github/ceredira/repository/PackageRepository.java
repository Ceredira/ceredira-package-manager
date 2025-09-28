package com.github.ceredira.repository;

import com.github.ceredira.model.Package;

import java.util.List;
import java.util.Optional;

public interface PackageRepository {
    Optional<com.github.ceredira.model.Package> findByName(String name);
    List<com.github.ceredira.model.Package> findAll();
    List<Package> search(String query);
}
