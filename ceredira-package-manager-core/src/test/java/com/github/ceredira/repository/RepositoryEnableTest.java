package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryEnableTest extends BaseTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
        manager.addRepository("repo1", "https://example.com/repo1.git");
        manager.addRepository("repo2", "https://example.com/repo2.git");
        manager.addRepository("", "https://example.com/empty.git");
    }

    @Test
    void shouldEnableExistingRepository() {
        // Убедимся, что изначально отключён
        Repository repo = manager.getRepositories().get("repo1");
        assertFalse(repo.isEnabled());

        manager.enable("repo1");

        assertTrue(repo.isEnabled());
    }

    @Test
    void shouldEnableRepositoryWithEmptyName() {
        Repository repo = manager.getRepositories().get("");
        assertFalse(repo.isEnabled());

        manager.enable("");

        assertTrue(repo.isEnabled());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRepositoryNameIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.enable(null)
        );
        assertNotNull(exception.getMessage());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRepositoryNotFound() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.enable("non-existent")
        );
        assertNotNull(exception.getMessage());
    }
}
