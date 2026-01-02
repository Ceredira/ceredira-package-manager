package com.github.ceredira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalPackage {
    private String name;
    private String version;
    private String size;
    private String downloadFileUrl;
    private String downloadFileName;
    private String author;
    private String website;
    private Map<String, String> changelog = new HashMap<>();
}
