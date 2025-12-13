package com.github.ceredira.manager;

import com.github.ceredira.utils.FileUtils;
import com.github.ceredira.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

public class PackageManagerListTest {
    private static final String rootFolder = TestUtils.getTestFolder().getAbsolutePath();

    @BeforeAll
    public static void setUp() {
        PackageManager pm = new PackageManager();

        pm.init();

        File repositoryOriginYaml = new File("src/test/resources/repositoryOrigin.yaml");
        File destinationFile = new File(rootFolder, "var/cpm/origin/repository.yaml");
        FileUtils.createFileWithContent(destinationFile, repositoryOriginYaml);

        File repositoryOriginIndexYaml = new File("src/test/resources/index.yaml");
        File destinationIndexFile = new File(rootFolder, "var/cpm/origin/index.yaml");
        FileUtils.createFileWithContent(destinationIndexFile, repositoryOriginIndexYaml);
    }

    @Test
    public void init() {
        PackageManager pm = new PackageManager();

        Set<String> packages = pm.list("origin", false);
        System.out.println( "packages: " + packages);
    }
}
