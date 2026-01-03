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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AddWithNameAndUrlTest extends BaseTest {
    private RepositoryManager manager;
    private final URI url = URI.create("https://example.com/my-repo.git");
    private final URI url1 = URI.create("https://old-url.com/my-repo.git");
    private final URI url2 = URI.create("https://new-url.com/my-repo.git");

    static Stream<Arguments> provideEmptyOrBlankUrls() {
        return Stream.of(
                Arguments.of((URI) null)
        );
    }

    static Stream<Arguments> provideIncorrectUrls() {
        return Stream.of(
                Arguments.of("null"),
                Arguments.of("mailto:null"),
                Arguments.of("mailto:test@example.com"),
                Arguments.of("123"),
                Arguments.of("http://"),
                Arguments.of("https://"),
                Arguments.of("ftp://"),
                Arguments.of("//example.com"),
                Arguments.of("://example.com"),
                Arguments.of("javascript:alert(1)"),
                Arguments.of("file:///etc/passwd"),
                Arguments.of("http://example.com/path with spaces"),
                Arguments.of(" ")

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

        manager.addRepository(name, String.valueOf(url));

        assertEquals(1, manager.getRepositories().size());
        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals(url, repo.getUrl());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankUrls")
    void shouldAddRepositoryWithEmptyOrBlankUrl(String emptyUrl) {
        String name = "repo3";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(name, emptyUrl)
        );

        assertEquals("URL cannot be null", exception.getMessage()
        );
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectUrls")
    void shouldAddRepositoryWithIncorrectUrl(String invalidUrl) {
        String name = "repo3";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(name, invalidUrl)
        );

        assertTrue(
                exception.getMessage().startsWith("Invalid URL format: "),
                "Expected 'Invalid URL format: ...', but got: " + exception.getMessage()
        );
    }

    @Test
    void addRepositoryWithCouldNotBeParsedAsUrl() {
        String name = "repo3";
        String invalidUrl = "http://example.com/path[file]";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(name, invalidUrl)
        );

        assertEquals("String could not be parsed as URI: " + invalidUrl, exception.getMessage()
        );
    }

    @Test
    void shouldReplaceExistingRepositoryWhenSameNameUsedWithUrl() {
        String name = "common-repo";

        manager.addRepository(name, String.valueOf(url1));

        manager.addRepository(name, String.valueOf(url2));

        assertEquals(1, manager.getRepositories().size());

        Repository repo = manager.getRepositories().get(name);
        assertEquals(name, repo.getName());
        assertEquals(url2, repo.getUrl().toString());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldAddRepositoryWithEmptyOrBlankName(String inputName) {

        manager.addRepository(inputName, String.valueOf(url));

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
