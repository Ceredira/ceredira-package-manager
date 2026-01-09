package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.removeRepository("non-existent");
        });

        assertAll("Grouped Assertions of Repository State",
                () -> assertEquals("Репозиторий 'non-existent' не найден. Операция невозможна.", exception.getMessage()),
                () -> assertTrue(manager.getRepositories().containsKey("repo1")),
                () -> assertTrue(manager.getRepositories().containsKey("repo2")),
                () -> assertEquals(2, manager.getRepositories().size())
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldRemoveRepositoryWithEmptyName(String inputName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.removeRepository(inputName);
        });

        assertAll("Grouped Assertions of Repository State",
                () -> assertEquals("Имя репозитория не может быть null или пустым", exception.getMessage()),
                () -> assertTrue(manager.getRepositories().containsKey("repo1")),
                () -> assertTrue(manager.getRepositories().containsKey("repo2")),
                () -> assertEquals(2, manager.getRepositories().size())
        );
    }
}
