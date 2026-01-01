package com.github.ceredira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpmPackage {
    private String name;
    private String version;
    private String size;
    private Map<String, String> description = new HashMap<>();
    private String author;
    private Map<String, String> changelog = new HashMap<>();
}
