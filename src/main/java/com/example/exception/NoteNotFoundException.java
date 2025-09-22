package com.example.exception;

public class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(Long id) {
        super("Нотатку за таким ID: " + id + " не знайдено");
    }
}
