package com.github.ceredira.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.github.ceredira.model.RepositoriesConfig;
import com.github.ceredira.model.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class RepositoryManager {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
            .enable(YAMLGenerator.Feature.USE_PLATFORM_LINE_BREAKS));


    public static List<Repository> loadRepositories(String filePath) {
        Path path = Path.of(filePath);
        if (!java.nio.file.Files.exists(path)) {
            throw new RuntimeException("YAML file not found: " + filePath);
        }
        RepositoriesConfig config = null;
        try {
            config = objectMapper.readValue(path.toFile(), RepositoriesConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config.getRepositories();
    }

    public static void saveRepositories(String filePath, List<Repository> repositories) {
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(
                JsonInclude.Include.NON_EMPTY,
                JsonInclude.Include.NON_NULL
        ));

        RepositoriesConfig config = new RepositoriesConfig();
        config.setRepositories(repositories);
        try {
            objectMapper.writeValue(Path.of(filePath).toFile(), config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
