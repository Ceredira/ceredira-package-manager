package com.github.ceredira.utils;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class NetUtils {

    public static void downloadFile(String fileUrl, String destPath) throws Exception {
        URL url = new URL(fileUrl);
        downloadFile(url, destPath);
    }

    /**
     * Скачивает файл по ссылке и сохраняет его по указанному пути.
     *
     * @param fileUrl  Прямая ссылка на файл
     * @param destPath Путь, по которому файл будет сохранен (включая имя файла)
     * @throws Exception Если произошла ошибка ввода-вывода или сети
     */
    public static void downloadFile(URL url, String destPath) throws Exception {
        try (InputStream in = url.openStream()) {
            Path target = Paths.get(destPath);
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Склеивает базовый URL с путем и проверяет его валидность.
     *
     * @param base корень сайта (напр. 127.0.0.1)
     * @param path путь к файлу (напр. index.yaml)
     * @return Полный URL в виде строки
     * @throws Exception если URL невалиден
     */
    public static URL combineAndValidate(String base, String path) throws Exception {
        try {
            // 1. Создаем объект URI из базовой строки
            URI baseUri = new URI(base);

            // 2. Склеиваем. Метод resolve автоматически следит за правильным количеством слешей.
            URI combinedUri = baseUri.resolve(path);

            // 3. Превращаем в URL для финальной проверки (протокол, формат)
            URL finalUrl = combinedUri.toURL();

            return finalUrl;
        } catch (URISyntaxException e) {
            throw new Exception("Некорректный синтаксис URI: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new Exception("Неверный формат аргументов: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Ошибка валидации URL: " + e.getMessage());
        }
    }
}
