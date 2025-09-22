package com.example.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteNotFoundExceptionTest {
    @Test
    void testConstructorWithMessage() {
        //Given
        String errorMessege = "Тестове повідомлення про помилку";

        //When
        NoteNotFoundException exception = new NoteNotFoundException(errorMessege);

        //Then
        assertEquals(errorMessege, exception.getMessage());
    }

    @Test
    void testConstructorWithId() {
        //Given
        Long id = 10L;

        //When
        NoteNotFoundException exception = new NoteNotFoundException(id);

        //Then
        assertEquals("Нотатку за таким ID: " + id + " не знайдено", exception.getMessage());
    }
}