package com.example.integration;

import com.example.entity.Note;
import com.example.repository.NoteRepository;
import com.example.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoteServiceRepositoryIntegrationTest {

    @Autowired
    private NoteService noteService;
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Test
    void testAddAndGetNote() {
        // Given
        Note newNote = new Note(null, "Інтеграційний тест", "Контент для інтеграційного тесту");
        
        // When
        Note savedNote = noteService.add(newNote);
        
        // Then
        assertNotNull(savedNote.getId());
        
        // Перевіряємо, що нотатка дійсно збережена в базі даних
        assertTrue(noteRepository.existsById(savedNote.getId()));
        
        // Перевіряємо отримання нотатки через сервіс
        Note retrievedNote = noteService.getById(savedNote.getId());
        assertEquals("Інтеграційний тест", retrievedNote.getTitle());
    }
    
    @Test
    void testUpdateNote() {
        // Given
        Note originalNote = noteService.add(new Note(null, "Оригінальний заголовок", "Оригінальний контент"));
        
        // When
        originalNote.setTitle("Оновлений заголовок");
        noteService.update(originalNote);
        
        // Then
        Note updatedNote = noteRepository.findById(originalNote.getId()).orElse(null);
        assertNotNull(updatedNote);
        assertEquals("Оновлений заголовок", updatedNote.getTitle());
    }
    
    @Test
    void testDeleteNote() {
        // Given
        Note noteToDelete = noteService.add(new Note(null, "Видалити мене", "Контент для видалення"));
        
        // When
        noteService.deleteById(noteToDelete.getId());
        
        // Then
        assertFalse(noteRepository.existsById(noteToDelete.getId()));
    }
}
