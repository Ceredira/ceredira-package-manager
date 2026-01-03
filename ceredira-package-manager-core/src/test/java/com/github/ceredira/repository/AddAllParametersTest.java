package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AddAllParametersTest extends BaseTest {
    private RepositoryManager manager;
    URI url = URI.create("https://example.com/my-repo.git");
    URI url1 = URI.create("https://old-url.com/my-repo.git");
    URI url2 = URI.create("https://new-url.com/my-repo.git");

    static Stream<Arguments> provideEmptyOrNullOriginalOptions() {
        return Stream.of(
                Arguments.of((Map<String, String>) null),
                Arguments.of(Map.of()),
                Arguments.of(Collections.emptyMap())
        );
    }

    static Stream<Arguments> provideValidNames() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("\t\n")
        );
    }

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
    }

    @Test
    void shouldAddRepositoryWithNameUrlAndOptions() {
        String name = "my-repo";
        Map<String, String> options = new HashMap<>();
        options.put("branch", "main");
        options.put("depth", "1");

        manager.addRepository(name, String.valueOf(url), options);

        assertEquals(1, manager.getRepositories().size());
        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals(url, repo.getUrl());
        assertEquals(options, repo.getProperties()); // копия, а не оригинал
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrNullOriginalOptions")
    void shouldAddRepositoryWithEmptyOrNullOriginalOptions(Map<String, String> options) {
        String name = "repo-options-test";

        manager.addRepository(name, String.valueOf(url), options);

        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals(url, repo.getUrl());

        // Ожидаем, что options никогда не null, но может быть пустой
        assertNotNull(repo.getProperties());
        assertTrue(repo.getProperties().isEmpty());
    }

    @Test
    void shouldAddRepositoryWithEmptyOptions() {
        String name = "repo-empty-options";
        Map<String, String> emptyOptions = new HashMap<>();

        manager.addRepository(name, String.valueOf(url), emptyOptions);

        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertEquals(url, repo.getUrl());
        assertNotNull(repo.getProperties());
        assertTrue(repo.getProperties().isEmpty());
        assertNotSame(emptyOptions, repo.getProperties()); // тоже должна быть копия
    }

    @Test
    void shouldAddRepositoryWithEmptyEntriesInOptions() {
        String name = "repo-weird-options";
        Map<String, String> options = Map.of("", "");

        manager.addRepository(name, String.valueOf(url), options);

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo.getProperties());
        assertFalse(repo.getProperties().isEmpty()); // важно: НЕ пусто!
        assertEquals("", repo.getProperties().get(""));
    }

    @Test
        // ToDo предусмотреть ошибку типа "Такой репозиторий уже существует"
    void shouldReplaceRepositoryWhenSameNameUsed() {
        String name = "duplicate";
        Map<String, String> opts1 = Map.of("v", "1");
        Map<String, String> opts2 = Map.of("v", "2");

        manager.addRepository(name, String.valueOf(url1), opts1);
        manager.addRepository(name, String.valueOf(url2), opts2);

        assertEquals(1, manager.getRepositories().size());

        Repository repo = manager.getRepositories().get(name);
        assertEquals(url2, repo.getUrl());
        assertEquals(opts2, repo.getProperties());
    }

    @Test
    void shouldThrowWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(null, null, null)
        );

        assertEquals("Repository name must not be null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideValidNames")
    void shouldAddRepositoryWithVariousValidNames(String inputName) {
        Map<String, String> options = Map.of("branch", "main");

        manager.addRepository(inputName, String.valueOf(url), options);

        String expectedName = inputName.trim();

        assertTrue(manager.getRepositories().containsKey(expectedName));

        Repository repo = manager.getRepositories().get(expectedName);
        assertNotNull(repo);
        assertEquals(expectedName, repo.getName());
        assertEquals(url, repo.getUrl());
        assertEquals(options, repo.getProperties());
        assertNotSame(options, repo.getProperties()); // убедимся, что мапа скопирована
    }
}
