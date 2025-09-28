package com.github.ceredira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageInfo {
    private String schemaVersion;
    private List<String> arch;
    private List<String> os;
    private Map<String, List<String>> category;
    private CpmPackage cpmPackage;
    private OriginalPackage originalPackage;
    private List<String> dependencies;
    private List<String> files;
    private List<String> metaFiles;
    private List<PackageFile> packageFiles;
}
