package com.github.ceredira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    private String name;
    private String description;
    private URI url;
    private String type;
    private boolean enabled;
    private Map<String, String> properties;
}
