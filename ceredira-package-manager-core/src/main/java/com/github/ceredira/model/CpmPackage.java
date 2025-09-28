package com.github.ceredira.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpmPackage {
    private String name;
    private String version;
    private String size;
    private Map<String, String> description;
    private String author;
    private Map<String, String> changelog;
}