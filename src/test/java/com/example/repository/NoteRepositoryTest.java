package com.example.repository;

import com.example.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoteRepositoryTest {
    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        // Додаємо тестові дані
        noteRepository.save(new Note(null, "Заголовок 1", "Контент 1"));
        noteRepository.save(new Note(null, "Заголовок 2", "Контент 2"));
    }

    @Test
    void testFindAll() {
        // When
        List<Note> notes = noteRepository.findAll();
        
        // Then
        assertEquals(2, notes.size());
        assertEquals("Заголовок 1", notes.get(0).getTitle());
        assertEquals("Заголовок 2", notes.get(1).getTitle());
    }

    @Test
    void testFindById_ExistingId() {
        // Given
        Note savedNote = noteRepository.save(new Note(null, "Заголовок 3", "Контент 3"));
        
        // When
        Optional<Note> foundNote = noteRepository.findById(savedNote.getId());
        
        // Then
        assertTrue(foundNote.isPresent());
        assertEquals("Заголовок 3", foundNote.get().getTitle());
    }

    @Test
    void testFindById_NonExistingId() {
        // When
        Optional<Note> foundNote = noteRepository.findById(999L);
        
        // Then
        assertFalse(foundNote.isPresent());
    }

    @Test
    void testSave_NewNote() {
        // Given
        Note newNote = new Note(null, "Новий заголовок", "Новий контент");
        
        // When
        Note savedNote = noteRepository.save(newNote);
        
        // Then
        assertNotNull(savedNote.getId());
        assertEquals("Новий заголовок", savedNote.getTitle());
        
        // Перевіряємо, що нотатка дійсно збережена в репозиторії
        Optional<Note> foundNote = noteRepository.findById(savedNote.getId());
        assertTrue(foundNote.isPresent());
    }

    @Test
    void testSave_UpdateNote() {
        // Given
        Note savedNote = noteRepository.save(new Note(null, "Заголовок для оновлення", "Контент для оновлення"));
        savedNote.setTitle("Оновлений заголовок");
        savedNote.setContent("Оновлений контент");
        
        // When
        Note updatedNote = noteRepository.save(savedNote);
        
        // Then
        assertEquals(savedNote.getId(), updatedNote.getId());
        assertEquals("Оновлений заголовок", updatedNote.getTitle());
        assertEquals("Оновлений контент", updatedNote.getContent());
        
        // Перевіряємо, що оновлення відбулося в репозиторії
        Optional<Note> foundNote = noteRepository.findById(savedNote.getId());
        assertTrue(foundNote.isPresent());
        assertEquals("Оновлений заголовок", foundNote.get().getTitle());
    }

    @Test
    void testExistsById_ExistingId() {
        // Given
        Note savedNote = noteRepository.save(new Note(null, "Заголовок", "Контент"));
        
        // When
        boolean exists = noteRepository.existsById(savedNote.getId());
        
        // Then
        assertTrue(exists);
    }

    @Test
    void testExistsById_NonExistingId() {
        // When
        boolean exists = noteRepository.existsById(999L);
        
        // Then
        assertFalse(exists);
    }

    @Test
    void testDeleteById_ExistingId() {
        // Given
        Note savedNote = noteRepository.save(new Note(null, "Заголовок для видалення", "Контент для видалення"));
        
        // When
        noteRepository.deleteById(savedNote.getId());
        
        // Then
        assertFalse(noteRepository.existsById(savedNote.getId()));
    }

    @Test
    void testDeleteById_NonExistingId() {
        // Given
        long initialSize = noteRepository.findAll().size();
        
        // When
        noteRepository.deleteById(999L);
        
        // Then
        assertEquals(initialSize, noteRepository.findAll().size());
    }
}