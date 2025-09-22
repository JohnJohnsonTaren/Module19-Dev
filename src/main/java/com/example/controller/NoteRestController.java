package com.example.controller;

import com.example.dto.NoteRequestDto;
import com.example.dto.NoteResponseDto;
import com.example.entity.Note;
import com.example.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.mapper.NoteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
public class NoteRestController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> listAllNotes() {
        List<Note> notes = noteService.listAll();
        return ResponseEntity.ok(noteMapper.toDtoList(notes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        Note note = noteService.getById(id);
        return ResponseEntity.ok(noteMapper.toDto(note));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto noteDto) {
        Note noteToCreate = noteMapper.toEntity(noteDto);
        Note createdNote = noteService.add(noteToCreate);
        return ResponseEntity.ok(noteMapper.toDto(createdNote));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDto noteDto) {
        Note noteToUpdate = noteMapper.toEntity(id, noteDto);
        Note updatedNote = noteService.update(noteToUpdate);
        return ResponseEntity.ok(noteMapper.toDto(updatedNote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}