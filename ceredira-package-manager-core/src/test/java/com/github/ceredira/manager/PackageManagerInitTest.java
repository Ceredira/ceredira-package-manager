package com.github.ceredira.manager;

import com.github.ceredira.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PackageManagerInitTest {

    private static final String rootFolder = TestUtils.getTestFolder().getAbsolutePath();

    @Test
    public void init() {
        PackageManager pm = new PackageManager();

        pm.init();
    }
}