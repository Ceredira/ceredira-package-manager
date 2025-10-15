package com.github.ceredira.utils;

import com.github.ceredira.model.PackageInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class PackageMetadataLoader {

    public static PackageInfo getPackageInfo(String packageName) {
        File file = new File(packageName);
        PackageInfo packageInfo = YamlUtil.loadFromFile(file, PackageInfo.class);

        return packageInfo;
    }
}
