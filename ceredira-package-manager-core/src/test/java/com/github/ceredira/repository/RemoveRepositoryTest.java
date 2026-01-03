package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveRepositoryTest extends BaseTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();

        manager.addRepository("repo1", "https://example.com/repo1.git");
        manager.addRepository("repo2", "https://example.com/repo2.git");
    }

    @Test
    void shouldRemoveExistingRepository() {
        manager.removeRepository("repo1");

        assertFalse(manager.getRepositories().containsKey("repo1"));
        assertTrue(manager.getRepositories().containsKey("repo2"));
        assertEquals(1, manager.getRepositories().size());
    }

    @Test
    void shouldDoNothingWhenRemovingNonExistentRepository() {
        manager.removeRepository("non-existent");

        assertTrue(manager.getRepositories().containsKey("repo1"));
        assertTrue(manager.getRepositories().containsKey("repo2"));
        assertEquals(2, manager.getRepositories().size());
    }

    @Test
    void shouldRemoveRepositoryWithEmptyName() {
        manager.removeRepository("");

        assertFalse(manager.getRepositories().containsKey(""));
        assertEquals(2, manager.getRepositories().size());
    }

    @Test
    void shouldDoNothingWhenRepositoryNameIsNull() {
        int initialSize = manager.getRepositories().size();

        manager.removeRepository(null); // не должно упасть и не должно изменить мапу

        assertEquals(initialSize, manager.getRepositories().size());
        assertTrue(manager.getRepositories().containsKey("repo1"));
        assertTrue(manager.getRepositories().containsKey("repo2"));
        // assertTrue(manager.getRepositories().containsKey(""));
    }
}
