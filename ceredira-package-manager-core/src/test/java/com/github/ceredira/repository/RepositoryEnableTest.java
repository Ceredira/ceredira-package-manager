package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryEnableTest extends BaseTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
        manager.addRepository("repo1", "https://example.com/repo1.git");
        manager.addRepository("repo2", "https://example.com/repo2.git");
        // manager.addRepository("", "https://example.com/empty.git");
    }

    @Test
    void shouldEnableExistingRepository() {
        // Убедимся, что изначально отключён
        Repository repo = manager.getRepositories().get("repo1");
        assertFalse(repo.isEnabled());

        manager.enable("repo1");

        assertTrue(repo.isEnabled());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldThrowExceptionOnInvalidName(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.enable(invalidName);
        });

        assertEquals("Имя репозитория не может быть null или пустым", exception.getMessage());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRepositoryNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.enable("non-existent")
        );
        assertEquals("Репозиторий 'non-existent' не найден. Операция невозможна.", exception.getMessage());
    }
}
