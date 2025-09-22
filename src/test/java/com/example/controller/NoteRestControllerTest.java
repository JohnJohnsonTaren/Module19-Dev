package com.example.controller;

import com.example.dto.NoteRequestDto;
import com.example.dto.NoteResponseDto;
import com.example.entity.Note;
import com.example.mapper.NoteMapper;
import com.example.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(NoteRestController.class)
@WithMockUser
public class NoteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private NoteMapper noteMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listAllNotes_shouldReturnAllNotes() throws Exception {
        // Arrange
        Note note = new Note(1L, "Test Title", "Test Content");
        NoteResponseDto responseDto = new NoteResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test Title");
        responseDto.setContent("Test Content");

        when(noteService.listAll()).thenReturn(Collections.singletonList(note));
        when(noteMapper.toDtoList(any())).thenReturn(Collections.singletonList(responseDto));

        // Act & Assert
        mockMvc.perform(get("/api/v1/notes")
                        .with(user("user")) // Explicitly add mock user for authentication
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Test Title")));
    }

    @Test
    void getNoteById_shouldReturnNote() throws Exception {
        // Arrange
        Note note = new Note(1L, "Test Title", "Test Content");
        NoteResponseDto responseDto = new NoteResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test Title");
        responseDto.setContent("Test Content");

        when(noteService.getById(anyLong())).thenReturn(note);
        when(noteMapper.toDto(any(Note.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/notes/1")
                        .with(user("user")) // Explicitly add mock user
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Title")));
    }

    @Test
    void createNote_shouldCreateAndReturnNote() throws Exception {
        // Arrange
        NoteRequestDto newRequestDto = new NoteRequestDto();
        newRequestDto.setTitle("New Title");
        newRequestDto.setContent("New Content");

        Note newNote = new Note();
        newNote.setId(1L);
        newNote.setTitle("New Title");
        newNote.setContent("New Content");

        NoteResponseDto newResponseDto = new NoteResponseDto();
        newResponseDto.setId(1L);
        newResponseDto.setTitle("New Title");
        newResponseDto.setContent("New Content");

        when(noteMapper.toEntity(any(NoteRequestDto.class))).thenReturn(newNote);
        when(noteService.add(any(Note.class))).thenReturn(newNote);
        when(noteMapper.toDto(any(Note.class))).thenReturn(newResponseDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/notes")
                        .with(user("user")) // Explicitly add mock user
                        .with(csrf()) // Add CSRF token, good practice for POST requests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Title")));
    }

    @Test
    void updateNote_shouldUpdateAndReturnNote() throws Exception {
        // Arrange
        NoteRequestDto updatedRequestDto = new NoteRequestDto();
        updatedRequestDto.setTitle("Updated Title");
        updatedRequestDto.setContent("Updated Content");

        Note updatedNote = new Note();
        updatedNote.setId(1L);
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");

        NoteResponseDto updatedResponseDto = new NoteResponseDto();
        updatedResponseDto.setId(1L);
        updatedResponseDto.setTitle("Updated Title");
        updatedResponseDto.setContent("Updated Content");

        when(noteMapper.toEntity(anyLong(), any(NoteRequestDto.class))).thenReturn(updatedNote);
        when(noteService.update(any(Note.class))).thenReturn(updatedNote);
        when(noteMapper.toDto(any(Note.class))).thenReturn(updatedResponseDto);

        // Act & Assert
        mockMvc.perform(put("/api/v1/notes/1")
                        .with(user("user")) // Explicitly add mock user
                        .with(csrf()) // Add CSRF token, good practice for PUT requests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")));
    }

    @Test
    void deleteNote_shouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(noteService).deleteById(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/notes/1")
                        .with(user("user")) // Explicitly add mock user
                        .with(csrf())) // Add CSRF token
                .andExpect(status().isNoContent());
    }
}
