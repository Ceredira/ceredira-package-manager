package com.github.ceredira.loader;

import com.github.ceredira.model.Package;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;

public class PackageMetadataLoader {
    private final Yaml yaml = new Yaml();

    public Package loadFromYaml(InputStream inputStream) {
        // Парсинг YAML в объект Package
        return null;
    }
}
