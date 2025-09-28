package com.github.ceredira.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void init() {
        // Массив с названиями каталогов
        String[] directories = {"bin", "etc", "home", "lib", "log", "man", "opt", "tmp", "var"};

        // Создание каталогов
        for (String dirName : directories) {
            File directory = new File(dirName);
            if (!directory.exists()) {
                boolean created = directory.mkdir();
                if (created) {
                    System.out.println("Каталог " + dirName + " создан.");
                } else {
                    System.err.println("Не удалось создать каталог " + dirName);
                }
            } else {
                System.out.println("Каталог " + dirName + " уже существует.");
            }
        }

        // Создание файлов
        String[] files = {".gitignore", "README.md"};
        for (String fileName : files) {
            File file = new File(fileName);
            try {
                if (!file.exists()) {
                    boolean created = file.createNewFile();
                    if (created) {
                        System.out.println("Файл " + fileName + " создан.");
                        // Заполнение README.md базовым содержимым
                        if (fileName.equals("README.md")) {
                            FileWriter writer = new FileWriter(file);
                            writer.write("# Проект\n");
                            writer.write("Описание проекта.\n");
                            writer.close();
                            System.out.println("Файл README.md заполнен базовым содержимым.");
                        }
                    } else {
                        System.err.println("Не удалось создать файл " + fileName);
                    }
                } else {
                    System.out.println("Файл " + fileName + " уже существует.");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла " + fileName + ": " + e.getMessage());
            }
        }
    }
}
