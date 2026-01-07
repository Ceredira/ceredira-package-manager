package com.github.ceredira.utils;

import com.github.ceredira.BaseTest;
import com.github.ceredira.model.PackageRevision;
import com.github.ceredira.model.PackageVersion;
import com.github.ceredira.model.RepositoryIndex;
import com.github.ceredira.model.RepositoryPackage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class RepositoryIndexUtilsTest extends BaseTest {

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void getRepositoryIndex() {
        RepositoryIndex repository = RepositoryIndexUtils.getRepositoryIndex("src/test/resources/index.yaml");
        assertNotNull(repository);

        log.info(repository.toString());
    }

    @Test
    void saveRepositoryIndex() {
        PackageRevision pr1 = new PackageRevision("11111111111111111111111111111111111111111111111111");
        PackageRevision pr2 = new PackageRevision("22222222222222222222222222222222222222222222222222");
        PackageRevision pr3 = new PackageRevision("33333333333333333333333333333333333333333333333333");

        PackageVersion pv1 = new PackageVersion();
        pv1.getRevisions().put("r1", pr1);
        pv1.getRevisions().put("r2", pr2);


        PackageVersion pv2 = new PackageVersion();
        pv2.getRevisions().put( "r1", pr3);

        RepositoryPackage rp1 = new RepositoryPackage();
        rp1.getVersions().put("1.0", pv1);
        rp1.getVersions().put("2.0", pv2);


        RepositoryPackage rp2 = new RepositoryPackage();
        rp2.getVersions().put("1.0", pv1);

        RepositoryIndex repositoryIndex = new RepositoryIndex();
        repositoryIndex.getPackages().put("everything", rp1);
        repositoryIndex.getPackages().put("fnr",  rp2);

        RepositoryIndexUtils.saveRepositoryIndex("target/index.yaml", repositoryIndex);
    }
}