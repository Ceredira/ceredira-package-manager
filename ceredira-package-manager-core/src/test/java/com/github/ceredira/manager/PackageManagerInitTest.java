package com.github.ceredira.manager;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PackageManagerInitTest {

    @Test
    public void init() {
        PackageManager pm = new PackageManager();

        pm.init();
    }
}