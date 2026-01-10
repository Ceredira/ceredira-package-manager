package com.github.ceredira.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class Repository {

    private static final String URL_REGEX = "^(https?|ftp)://[^\s/$.?#].[^\s]*$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

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
        this.url = validateUrl(url);

        this.enabled = false;
        type = RepositoryType.REMOTE;
    }

    public Repository(String name, String url, Map<String, String> properties) {
        this.name = validateName(name);
        this.url = validateUrl(url);
        this.properties = new HashMap<>();

        if (properties != null) {
            this.properties.putAll(properties);
        }
    }

    // ToDo доработать валидацию, чтобы не создавались репозитории с пустыми именами (пустой строкой)
    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Repository name must not be null or blank");
        }
        return name.trim();
    }

    private static URI validateUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }

        String trimmedUrl = url.trim();

        if (!URL_PATTERN.matcher(trimmedUrl).matches()) {
            throw new IllegalArgumentException("Invalid URL format: " + trimmedUrl);
        }

        try {
            return URI.create(trimmedUrl);
        } catch (Exception e) {
            throw new IllegalArgumentException("String could not be parsed as URI: " + trimmedUrl, e);
        }
    }

    // Если нужно будет засетить и сохранить валидацию
    public void setUrl(String url) {
        this.url = validateUrl(url);
    }
}
