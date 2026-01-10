package com.github.ceredira.packages;

import com.github.ceredira.BaseTest;
import com.github.ceredira.model.CpmPackage;
import com.github.ceredira.model.OriginalPackage;
import com.github.ceredira.model.PackageFile;
import com.github.ceredira.model.PackageInfo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.github.ceredira.utils.FileUtils.getFileContentAsString;
import static com.github.ceredira.utils.YamlUtils.loadFromFile;
import static com.github.ceredira.utils.YamlUtils.saveToFile;
import static org.junit.jupiter.api.Assertions.*;

public class PackageInfoTest extends BaseTest {
    private static PackageInfo getTestPackageInfo() {
        PackageInfo packageInfo = new PackageInfo();

        packageInfo.setSchemaVersion("1");
        packageInfo.getArch().add("x86_64");
        packageInfo.getOs().add("windows");

        packageInfo.setCategory(Map.of(
                "en", List.of("tools", "search"),
                "ru", List.of("утилиты", "поиск")
        ));

        CpmPackage cpmPackage = getTestCpmPackage();
        packageInfo.setCpmPackage(cpmPackage);

        OriginalPackage originalPackage = getTestOriginalPackage();
        packageInfo.setOriginalPackage(originalPackage);

        packageInfo.setDependencies(List.of("name.test.1", "name.test.2", "name.test.3"));
        packageInfo.setFiles(List.of(
                "bin\\Everything\\Everything.exe",
                "bin\\Everything\\Everything.lng"));

        packageInfo.setMetaFiles(List.of(
                "apps\\Everything.bat", "\\uninstall.bat"
        ));

        packageInfo.setPackageFiles(List.of(
                new PackageFile("everything-1.4.1.1028-r1.cpmf.7z", "${sha256}"),
                new PackageFile("everything-1.4.1.1028-r1.cpmm.7z", "${sha256}")
        ));
        return packageInfo;
    }

    private static CpmPackage getTestCpmPackage() {
        CpmPackage cpmPackage = new CpmPackage();
        cpmPackage.setName("everything");
        cpmPackage.setVersion("r1");
        cpmPackage.setSize("${package_size}");
        cpmPackage.setDescription(Map.of(
                "en", "Instantly search files and folders by their names",
                "ru", "Мгновенный поиск файлов и папок по их именам"
        ));
        cpmPackage.setAuthor("unixshaman@gmail.com");
        cpmPackage.setChangelog(Map.of(
                "en", "package created",
                "ru", "пакет создан")
        );
        return cpmPackage;
    }

    private static OriginalPackage getTestOriginalPackage() {
        OriginalPackage originalPackage = new OriginalPackage();
        originalPackage.setName("Everything");
        originalPackage.setVersion("1.4.1.1028");
        originalPackage.setSize("${original_package_size}");
        originalPackage.setDownloadFileUrl("https://www.voidtools.com/Everything-1.4.1.1028.x64.zip");
        originalPackage.setDownloadFileName("Everything-1.4.1.1028.x64.zip");
        originalPackage.setAuthor("David Carpenter (david.carpenter@voidtools.com)");
        originalPackage.setWebsite("https://www.voidtools.com/");
        originalPackage.setChangelog(Map.of(
                "en",
                """
                        Friday, 20 June 2025: Version 1.4.1.1028
                        fixed a crash when getting help text from a context menu item that throws an exception.
                        updated localization.""",
                "ru",
                """
                        Пятница, 20 июня 2025 г.: Версия 1.4.1.1028
                        Исправлен сбой при получении текста справки из пункта контекстного меню,который приводил к исключению.
                        Обновлена локализация."""
        ));
        return originalPackage;
    }

    @Test
    void setPackageInfo() {
        PackageInfo packageInfo = getTestPackageInfo();

        File destination = classRootPath.resolve("packageInfo.yaml").toFile();
        saveToFile(destination, packageInfo, PackageInfo.class);

        String result = getFileContentAsString(destination);

        File templateFile = new File("src/test/resources/packageInfo.yaml");
        String templateString = getFileContentAsString(templateFile);

        assertEquals(templateString, result, "Расхождение");
    }

    @Test
    void getPackageInfo() {
        File templateFile = new File("src/test/resources/packageInfo.yaml");

        PackageInfo packageInfo = loadFromFile(templateFile, PackageInfo.class);

        assertEquals("1", packageInfo.getSchemaVersion());
        assertTrue(packageInfo.getArch().contains("x86_64"));
        assertTrue(packageInfo.getOs().contains("windows"));

        Map<String, List<String>> category = packageInfo.getCategory();
        assertTrue(category.containsKey("en"));
        assertTrue(category.get("en").contains("tools"));
        assertTrue(category.get("en").contains("search"));
        assertTrue(category.containsKey("ru"));
        assertTrue(category.get("ru").contains("утилиты"));
        assertTrue(category.get("ru").contains("поиск"));

        CpmPackage cpm = packageInfo.getCpmPackage();
        assertNotNull(cpm);
        assertEquals("everything", cpm.getName());
        assertEquals("r1", cpm.getVersion());
        assertEquals("${package_size}", cpm.getSize());
        assertEquals("Instantly search files and folders by their names", cpm.getDescription().get("en"));
        assertEquals("Мгновенный поиск файлов и папок по их именам", cpm.getDescription().get("ru"));
        assertEquals("unixshaman@gmail.com", cpm.getAuthor());
        assertEquals("package created", cpm.getChangelog().get("en"));
        assertEquals("пакет создан", cpm.getChangelog().get("ru"));

        OriginalPackage orig = packageInfo.getOriginalPackage();
        assertEquals("Everything", orig.getName());
        assertEquals("1.4.1.1028", orig.getVersion());
        assertEquals("${original_package_size}", orig.getSize());
        assertEquals("https://www.voidtools.com/Everything-1.4.1.1028.x64.zip", orig.getDownloadFileUrl());
        assertEquals("Everything-1.4.1.1028.x64.zip", orig.getDownloadFileName());
        assertEquals("David Carpenter (david.carpenter@voidtools.com)", orig.getAuthor());
        assertEquals("https://www.voidtools.com/", orig.getWebsite());

        Map<String, String> expectedChangelog = Map.of(
                "en", """
                        Friday, 20 June 2025: Version 1.4.1.1028
                        fixed a crash when getting help text from a context menu item that throws an exception.
                        updated localization.""",
                "ru", """
                        Пятница, 20 июня 2025 г.: Версия 1.4.1.1028
                        Исправлен сбой при получении текста справки из пункта контекстного меню,который приводил к исключению.
                        Обновлена локализация."""
        );

        assertEquals(expectedChangelog, orig.getChangelog());

        List<String> deps = packageInfo.getDependencies();
        assertTrue(deps.contains("name.test.1"));
        assertTrue(deps.contains("name.test.2"));
        assertTrue(deps.contains("name.test.3"));

        assertTrue(packageInfo.getFiles().contains("bin\\Everything\\Everything.exe"));
        assertTrue(packageInfo.getMetaFiles().contains("apps\\Everything.bat"));

        List<PackageFile> packageFiles = packageInfo.getPackageFiles();

        PackageFile file1 = packageFiles.get(0);
        assertEquals("everything-1.4.1.1028-r1.cpmf.7z", file1.getFileName());
        assertEquals("${sha256}", file1.getSha256());

        PackageFile file2 = packageFiles.get(1);
        assertEquals("everything-1.4.1.1028-r1.cpmm.7z", file2.getFileName());
        assertEquals("${sha256}", file2.getSha256());
    }
}
