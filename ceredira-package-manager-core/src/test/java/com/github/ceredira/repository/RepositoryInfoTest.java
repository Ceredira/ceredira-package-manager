package com.github.ceredira.repository;

import com.github.ceredira.manager.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryInfoTest {
    private RepositoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new RepositoryManager();
        manager.addRepository("repo1", "https://example.com/repo1.git");
        manager.addRepository("repo2", "https://example.com/repo2.git");
        manager.addRepository("", "https://example.com/empty.git"); // пустое имя допустимо
    }

    @Test
    void shouldReturnInfoForExistingRepository() {
        String info = manager.info("repo1");

        assertNotNull(info);
        assertTrue(info.contains("repo1"));
        assertTrue(info.contains("https://example.com/repo1.git"));
    }

    @Test
    void shouldReturnInfoForRepositoryWithEmptyName() {
        String info = manager.info("");

        assertNotNull(info);
        assertTrue(info.contains("https://example.com/empty.git"));

        assertTrue(info.contains("name=''") || info.contains("name=\"\"") || info.contains("name=,"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRepositoryNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.info("non-existent")
        );

        assertEquals("Repository with name 'non-existent' not found", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenSearchingForNullName() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.info(null)
        );

        assertEquals("Repository with name 'null' not found", exception.getMessage());
    }
}
