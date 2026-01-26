package com.github.ceredira.utils;

import com.github.ceredira.model.PackageInfoParsed;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class InstallUtils {
    private static final Pattern PKG_PATTERN =
            Pattern.compile("^(.+)-([\\d.]+)(?:-(r\\d+))?$");

    public static PackageInfoParsed parse(String input) {
        Matcher matcher = PKG_PATTERN.matcher(input);

        if (matcher.find()) {
            return new PackageInfoParsed(
                    matcher.group(1), // Имя (например, postgresql-14)
                    matcher.group(2), // Версия (например, 15.2)
                    matcher.group(3)  // Ревизия (например, r1)
            );
        }

        // Если формат не подошел, считаем всю строку именем
        return new PackageInfoParsed(input, "latest", "r0");
    }
}
