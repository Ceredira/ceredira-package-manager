package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryListTest extends BaseTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
    }

    public void createTestRepositories() {
        manager.addRepository("repo1", "http://ceredira.org/repo1.git");
        manager.addRepository("repo2", "http://ceredira.org/repo2.git");
    }

    @Test
    void shouldReturnAllRepositoryNamesAsSet() {
        createTestRepositories();
        Set<String> names = manager.list();
        assertNotNull(names);
        assertEquals(2, names.size());
        assertTrue(names.contains("repo1"));
        assertTrue(names.contains("repo2"));
    }

    @Test
    void shouldReturnEmptySetWhenNoRepositories() {
        RepositoryManager emptyManager = new RepositoryManager();

        Set<String> names = emptyManager.list();

        assertNotNull(names);
        assertTrue(names.isEmpty());
    }

    @Test
    void shouldReflectChangesInUnderlyingMap() {
        createTestRepositories();
        Set<String> names = manager.list();

        manager.removeRepository("repo1");

        assertEquals(1, names.size());
        assertFalse(names.contains("repo1"));
    }

    // Опционально: если вы хотите защитить от модификации извне,
    // то этот тест покажет, что текущая реализация НЕ защищена:
    @Test
    void shouldAllowExternalModificationOfKeySet() {
        createTestRepositories();
        Set<String> names = manager.list();

        // Это упадёт с UnsupportedOperationException, если keySet() не поддерживает remove,
        // но у обычного HashMap — поддерживает!
        assertDoesNotThrow(() -> names.remove("repo1"));

        // После этого менеджер потерял репозиторий!
        assertFalse(manager.getRepositories().containsKey("repo1"));
    }
}
