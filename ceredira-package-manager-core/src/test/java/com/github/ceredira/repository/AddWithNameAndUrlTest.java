package com.github.ceredira.repository;

import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AddWithNameAndUrlTest {
    private RepositoryManager manager;

    static Stream<Arguments> provideEmptyOrBlankUrls() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("\t\n")
        );
    }

    static Stream<Arguments> provideEmptyOrBlankNames() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("\t\n"),
                Arguments.of(" \r\n  ")
        );
    }

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
    }

    @Test
    void shouldAddRepositoryWithNameAndUrl() {
        String name = "repo1";
        String url = "https://example.com/repo1.git";

        manager.addRepository(name, url);

        assertEquals(1, manager.getRepositories().size());
        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals(url, repo.getUrl());
    }

    // ToDo Строка, но она не пройдет внутри
    //  конструктора валидность по регулярному выражению на url поле.
    //  Может быть, стоит даже проверить 200 Ok
    @Test
    void shouldAddRepositoryWithNullUrl() {
        String name = "repo2";

        manager.addRepository(name, "null");
        // manager.addRepository(name, (String) null); - Тоже предусмотреть

        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals("null", repo.getUrl());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankUrls")
    void shouldAddRepositoryWithEmptyOrBlankUrl(String url) {
        String name = "repo3";

        manager.addRepository(name, url);

        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals(url, repo.getUrl()); // сохраняется как есть
    }

    @Test
    void shouldReplaceExistingRepositoryWhenSameNameUsedWithUrl() {
        String name = "common-repo";
        String url1 = "https://old.url/repo.git";
        String url2 = "https://new.url/repo.git";

        manager.addRepository(name, url1);

        manager.addRepository(name, url2);

        assertEquals(1, manager.getRepositories().size());

        Repository repo = manager.getRepositories().get(name);
        assertEquals(name, repo.getName());
        assertEquals(url2, repo.getUrl());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldAddRepositoryWithEmptyOrBlankName(String inputName) {
        String url = "https://example.com/repo.git  ";

        manager.addRepository(inputName, url);

        String expectedName = inputName.trim();

        assertTrue(manager.getRepositories().containsKey(expectedName));

        Repository repo = manager.getRepositories().get(expectedName);
        assertNotNull(repo);
        assertEquals(expectedName, repo.getName()); // имя именно после trim()
        assertEquals(url, repo.getUrl());            // URL сохраняется как есть
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNameIsNull() {
        String url = "https://example.com/repo.git  ";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(null, url)
        );

        assertEquals("Repository name must not be null", exception.getMessage());
    }
}
