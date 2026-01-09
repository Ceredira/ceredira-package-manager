package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class AddWithNameTest extends BaseTest {
    String name = "test-repo";
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
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
    @MethodSource("provideEmptyOrBlankNames")
    void shouldThrowExceptionOnInvalidName(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addRepository(invalidName);
        });

        assertEquals("Repository name must not be null or blank", exception.getMessage());
    }
}
