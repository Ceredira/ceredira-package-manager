package com.github.ceredira.model;

import lombok.*;

import java.util.List;

@Data
public class Package {
    private String name;
    private String version;
    private String description;
    private String url;
    private List<String> dependencies;
}
