package com.github.ceredira.repository;

import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static com.github.ceredira.utils.TestUtils.getTestFolder;
import static org.junit.jupiter.api.Assertions.*;

public class AddWithNameTest {
    static String emptyString = "";
    static String emptyStringWithSpace = "   ";
    static String emptyStringWithLineBreak = "\t\n";
    String name = "test-repo";
    private RepositoryManager manager;

    static Stream<Arguments> invalidNames() {
        return Stream.of(
                Arguments.of((String) null)
        );
    }

    static Stream<String> validButEmptyNames() {
        return Stream.of(emptyString, emptyStringWithSpace, emptyStringWithLineBreak);
    }

    @BeforeEach
    void setUp() {
        Path root = getTestFolder().toPath();
        manager = new RepositoryManager(root);
    }

    @Test
    void shouldAddRepositoryWithName() {
        manager.addRepository(name);

        assertEquals(1, manager.getRepositories().size());
        assertTrue(manager.getRepositories().containsKey(name));

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
    }

    // ToDo: Как будто нужно предусмотреть ошибку типа "Такой репозиторий уже существует"
    @Test
    void shouldReplaceRepositoryIfSameNameAddedTwice() {
        manager.addRepository(name);
        manager.addRepository(name);

        assertEquals(1, manager.getRepositories().size());

        Repository repo = manager.getRepositories().get(name);
        assertNotNull(repo);
        assertEquals(name, repo.getName());
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void shouldThrowExceptionOnInvalidName(String invalidName) {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addRepository(invalidName);
        });
    }

    @ParameterizedTest
    @MethodSource("validButEmptyNames")
    void shouldSuccessfullyAddRepositoryWithEmptyOrBlankName(String name) {
        manager.addRepository(name);

        assertTrue(manager.getRepositories().containsKey(emptyString));

        Repository repo = manager.getRepositories().get(emptyString);
        assertNotNull(repo);
        assertEquals(emptyString, repo.getName());
    }
}
