package com.github.ceredira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageInfo {
    private String schemaVersion;
    private List<String> arch = new ArrayList<>();
    private List<String> os = new ArrayList<>();
    private Map<String, List<String>> category = new HashMap<>();
    private CpmPackage cpmPackage;
    private OriginalPackage originalPackage;
    private List<String> dependencies = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private List<String> metaFiles = new ArrayList<>();
    private List<PackageFile> packageFiles = new ArrayList<>();
}
