package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AddWithNameAndOptionsTest extends BaseTest {
    private RepositoryManager manager;
    // String url = "https://example.com/repo.git";

    static Stream<Arguments> emptyOrNullableOptions() {
        return Stream.of(
                Arguments.of((Map<String, String>) null),
                Arguments.of(Collections.emptyMap()),
                Arguments.of(new HashMap<>())
        );
    }

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
    }

    @Test
    void shouldAddRepositoryWithNameAndOptions() {
        String name = "repo1";
        Map<String, String> options = new HashMap<>();
        options.put("key1", "value1");
        options.put("key2", "value2");

        manager.addRepository(name, options);

        assertEquals(1, manager.getRepositories().size());
        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertEquals(options, repo.getProperties());
        assertNotSame(options, repo.getProperties()); // проверяем, что была создана копия
    }

    @Test
    void shouldReplaceExistingRepositoryWhenSameNameUsed() {
        String name = "common-repo";
        Map<String, String> opts1 = Map.of("a", "1");
        Map<String, String> opts2 = Map.of("b", "2");

        manager.addRepository(name, opts1);

        manager.addRepository(name, opts2);

        // assertEquals(1, manager.getRepositories().size());
        Repository repo = manager.getRepositories().get(name);
        assertEquals(opts2, repo.getProperties());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldThrowWhenAddingRepositoryWithInvalidName(String invalidName) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addRepository(invalidName, urlExample)
        );

        assertEquals("Repository name must not be null or blank", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyOrNullableOptions")
    void shouldAddRepositoryWithEmptyOptions(Map<String, String> inputOptions) {
        String name = "test-repo";

        manager.addRepository(name, inputOptions);

        assertTrue(manager.getRepositories().containsKey(name));
        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
        assertNotNull(repo.getProperties());
        assertTrue(repo.getProperties().isEmpty());
    }
}
