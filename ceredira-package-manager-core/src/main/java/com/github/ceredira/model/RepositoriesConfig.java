package com.github.ceredira.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RepositoriesConfig {
    private Map<String, Repository> repositories;
}
