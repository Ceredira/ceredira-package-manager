package com.github.ceredira.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.ceredira.model.PackageInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

@Slf4j
public class PackageMetadataLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    public static PackageInfo getPackageInfo(String packageName) {
        try {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

            File yamlFile = new File(packageName);
            PackageInfo packageInfo = objectMapper.readValue(yamlFile, PackageInfo.class);

            return packageInfo;
        } catch (IOException e) {
            String yamlLoadError = MessageFormat.format(
                    "Error loading YAML for package \"{0}\": {1}",
                    packageName,
                    e.getMessage());

            log.error(yamlLoadError);
            throw new RuntimeException(yamlLoadError);
        }
    }
}
