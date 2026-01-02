package com.github.ceredira.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Repository {
    private String name;
    private String description;
    private URI url;
    RepositoryType type;
    private boolean enabled;
    private Map<String, String> properties = new HashMap<>();

    // Конструкторы для LOCAL типа
    public Repository(String name) {
        this.name = validateName(name);

        this.enabled = false;
        type = RepositoryType.LOCAL;
    }

    public Repository(String name, Map<String, String> properties) {
        this(name);

        this.properties = new HashMap<>();

        if (properties != null) {
            this.properties.putAll(properties);
        }
    }

    // Конструкторы для REMOTE типа
    public Repository(String name, String url) {
        this.name = validateName(name);
        this.url = URI.create(url);

        this.enabled = false;
        type = RepositoryType.REMOTE;
    }

    public Repository(String name, String url, Map<String, String> properties) {
        this(name, url);

        this.properties = new HashMap<>();

        if (properties != null) {
            this.properties.putAll(properties);
        }
    }

    private static String validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Repository name must not be null");
        }
        return name.trim();
    }
}
