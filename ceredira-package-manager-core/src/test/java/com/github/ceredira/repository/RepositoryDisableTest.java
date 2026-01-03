package com.github.ceredira.repository;

import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryDisableTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
        manager.addRepository("repo1", "https://example.com/repo1.git");
        manager.addRepository("repo2", "https://example.com/repo2.git");
        manager.addRepository("", "https://example.com/empty.git");
    }

    @Test
    void shouldDisableExistingRepository() {
        // Предположим, что по умолчанию репозиторий включён
        manager.enable("repo1");
        assertTrue(manager.getRepositories().get("repo1").isEnabled());

        manager.disable("repo1");

        assertFalse(manager.getRepositories().get("repo1").isEnabled());
    }

    @Test
    void shouldDisableRepositoryWithEmptyName() {
        // Убедимся, что включён
        manager.enable("");
        assertTrue(manager.getRepositories().get("").isEnabled());

        manager.disable("");

        assertFalse(manager.getRepositories().get("").isEnabled());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRepositoryNameIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.disable(null)
        );
        assertNotNull(exception.getMessage());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRepositoryNotFound() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.disable("non-existent")
        );
        assertNotNull(exception.getMessage());
    }
}
