package com.example.service;

import com.example.entity.Note;
import com.example.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteServiceImpl;

    @Test
    void testListAll() {
        // Given
        Note note1 = new Note(1L, "Заголовок 1", "Контент 1");
        Note note2 = new Note(2L, "Заголовок 2", "Контент 2");
        List<Note> expectedNotes = Arrays.asList(note1, note2);
        when(noteRepository.findAll()).thenReturn(expectedNotes);
        
        // When
        List<Note> actualNotes = noteServiceImpl.listAll();
        
        // Then
        assertEquals(2, actualNotes.size());
        assertEquals(expectedNotes, actualNotes);
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testAdd() {
        // Given
        Note note = new Note(null, "Новий заголовок", "Новий контент");
        Note newNote = new Note(null, "Новий заголовок", "Новий контент");
        Note savedNote = new Note(1L, "Новий заголовок", "Новий контент");
        
        // Використовуємо anyLong() для гнучкого порівняння id
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);
        
        // When
        Note actualNote = noteServiceImpl.add(note);
        
        // Then
        assertNotNull(actualNote.getId());
        assertEquals(savedNote.getTitle(), actualNote.getTitle());
        assertEquals(savedNote.getContent(), actualNote.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void testDeleteById() {
        // Given
        Long id = 1L;
        when(noteRepository.existsById(id)).thenReturn(true);
        
        // When
        noteServiceImpl.deleteById(id);
        
        // Then
        verify(noteRepository).existsById(id);
        verify(noteRepository).deleteById(id);
    }

    @Test
    void testDeleteByIdNonExistent() {
        // Given
        Long id = 999L;
        when(noteRepository.existsById(id)).thenReturn(false);
        
        // When & Then
        assertThrows(RuntimeException.class, () -> noteServiceImpl.deleteById(id));
        verify(noteRepository).existsById(id);
        verify(noteRepository, never()).deleteById(id);
    }

    @Test
    void testUpdate() {
        // Given
        Note note = new Note(1L, "Оновлений заголовок", "Оновлений контент");
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        
        // When
        noteServiceImpl.update(note);
        
        // Then
        verify(noteRepository).findById(note.getId());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void testUpdateNonExistent() {
        // Given
        Note note = new Note(999L, "Неіснуючий заголовок", "Неіснуючий контент");
        when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(RuntimeException.class, () -> noteServiceImpl.update(note));
        verify(noteRepository).findById(note.getId());
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    void testGetById() {
        // Given
        Long id = 1L;
        Note expectedNote = new Note(id, "Заголовок", "Контент");
        when(noteRepository.findById(id)).thenReturn(Optional.of(expectedNote));
        
        // When
        Note actualNote = noteServiceImpl.getById(id);
        
        // Then
        assertEquals(expectedNote, actualNote);
        verify(noteRepository).findById(id);
    }

    @Test
    void testGetByIdNonExistent() {
        // Given
        Long id = 999L;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(RuntimeException.class, () -> noteServiceImpl.getById(id));
        verify(noteRepository).findById(id);
    }
}