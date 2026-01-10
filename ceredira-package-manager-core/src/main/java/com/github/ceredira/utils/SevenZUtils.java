package com.github.ceredira.utils;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class SevenZUtils {
    public static void decompress(File archivePath, File destinationPath) throws IOException {
        // Получаем абсолютный путь целевой папки для сравнения
        Path targetDir = destinationPath.toPath().toAbsolutePath().normalize();

        try (SevenZFile sevenZFile = SevenZFile.builder()
                .setFile(archivePath)
                .get()) {
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                // 1. Нормализуем путь из архива и объединяем с целевой папкой
                Path entryPath = targetDir.resolve(entry.getName()).normalize();

                // 2. ГЛАВНАЯ ПРОВЕРКА: путь файла должен начинаться с пути целевой папки
                if (!entryPath.startsWith(targetDir)) {
                    throw new IOException("Обнаружена попытка выхода за пределы папки: " + entry.getName());
                }

                File curFile = entryPath.toFile();

                if (entry.isDirectory()) {
                    curFile.mkdirs();
                } else {
                    // Создаем родительские директории, если их нет
                    File parent = curFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }

                    try (FileOutputStream out = new FileOutputStream(curFile)) {
                        byte[] content = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = sevenZFile.read(content)) != -1) {
                            out.write(content, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }
}
