package com.github.ceredira.repository;

import com.github.ceredira.BaseTest;
import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryInfoTest extends BaseTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
        manager.addRepository("repo1", "http://ceredira.org/repo1.git");
        manager.addRepository("repo2", "http://ceredira.org/repo2.git");
    }

    @Test
    void shouldReturnInfoForExistingRepository() {
        String info = manager.info("repo1");

        assertNotNull(info);
        assertTrue(info.contains("repo1"));
        assertTrue(info.contains("http://ceredira.org/repo1.git"));
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrBlankNames")
    void shouldReturnInfoForRepositoryWithEmptyName(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.info(invalidName);
        });

        assertEquals("Имя репозитория не может быть null или пустым", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRepositoryNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.info("non-existent")
        );

        assertEquals("Репозиторий 'non-existent' не найден. Операция невозможна.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenSearchingForNullName() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.info("null") // строка
        );

        assertEquals("Репозиторий 'null' не найден. Операция невозможна.", exception.getMessage());
    }
}
