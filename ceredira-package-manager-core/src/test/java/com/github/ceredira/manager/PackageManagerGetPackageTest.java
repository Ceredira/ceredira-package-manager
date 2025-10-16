package com.github.ceredira.manager;

import com.github.ceredira.model.PackageInfo;
import com.github.ceredira.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PackageManagerGetPackageTest {

    private static final String rootFolder = TestUtils.getTestFolder().getAbsolutePath();

    @Test
    public void getPackageInfo() {
        PackageManager pm = new PackageManager();

        PackageInfo packageInfo = pm.info("everything");

        log.info(packageInfo.toString());
    }
}