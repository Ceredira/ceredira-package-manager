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
    private final URI url = URI.create("http://ceredira.org/my-repo.git"); // http://desktop-cdp6mgl:1180/ ??
    private final URI url1 = URI.create("http://ceredira.org/my-repo1.git");
    private final URI url2 = URI.create("http://ceredira.org/my-repo2.git");

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
        assertEquals(url2, repo.getUrl());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldNotAddRepositoryWithEmptyOrBlankName(String inputName) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(inputName, String.valueOf(url))
        );

        assertEquals("Repository name must not be null or blank", exception.getMessage());
    }
}
