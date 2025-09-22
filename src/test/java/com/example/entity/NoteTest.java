package com.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    @Test
    void testConstructorAndGetters() {
        // Given
        Long id = 1L;
        String title = "Тестовий заголовок\n";
        String content = "Тестовий контент\n";

        // When
        Note note = new Note(id, title, content);

        // Then
        assertEquals(id, note.getId());
        assertEquals(title, note.getTitle());
        assertEquals(content, note.getContent());
    }

    @Test
    void testSetters() {
        // Given
        Note note = new Note();
        Long id = 2L;
        String title = "Новий заголовок\n";
        String content = "Новий контент\n";

        // When
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);

        // Then
        assertEquals(id, note.getId());
        assertEquals(title, note.getTitle());
        assertEquals(content, note.getContent());
    }

    @Test
    void testToString() {
        // Given
        Note note = new Note(3L, "Заголовок\n", "Контент\n");

        // When
        String noteString = note.toString();

        //Then
        assertTrue(noteString.contains("3"));
        assertTrue(noteString.contains("Заголовок"));
        assertTrue(noteString.contains("Контент"));
    }
}