package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryDisableTest extends BaseTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
        manager.addRepository("repo1", "https://example.com/repo1.git");
        manager.addRepository("repo2", "https://example.com/repo2.git");
    }

    @Test
    void shouldDisableExistingRepository() {
        // Предположим, что по умолчанию репозиторий включён
        manager.enable("repo1");
        assertTrue(manager.getRepositories().get("repo1").isEnabled());

        manager.disable("repo1");

        assertFalse(manager.getRepositories().get("repo1").isEnabled());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldDisableRepositoryWithEmptyName(String invalidName) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.disable(invalidName)
        );
        assertEquals("Имя репозитория не может быть null или пустым", exception.getMessage());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRepositoryNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.disable("non-existent")
        );
        assertEquals("Репозиторий 'non-existent' не найден. Операция невозможна.", exception.getMessage());
    }
}
