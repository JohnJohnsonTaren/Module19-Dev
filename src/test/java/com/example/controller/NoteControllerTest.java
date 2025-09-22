package com.example.controller;

import com.example.entity.Note;
import com.example.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    @WithMockUser
    void testListAllNotes() throws Exception {
        // Given
        Note note1 = new Note(1L, "Заголовок 1", "Контент 1");
        Note note2 = new Note(2L, "Заголовок 2", "Контент 2");
        List<Note> notes = Arrays.asList(note1, note2);
        when(noteService.listAll()).thenReturn(notes);

        // When & Then
        mockMvc.perform(get("/note/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("notes", notes))
                .andExpect(view().name("note/list"));
    }

    @Test
    @WithMockUser
    void testGetNoteById() throws Exception {
        // Given
        Note note = new Note(1L, "Заголовок", "Контент");
        when(noteService.getById(1L)).thenReturn(note);

        // When & Then
        mockMvc.perform(get("/note/edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("note", note))
                .andExpect(view().name("note/edit"));
    }

    @Test
    @WithMockUser
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/note/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("note"))
                .andExpect(view().name("note/create"));
    }

    @Test
    @WithMockUser
    void testCreateNote() throws Exception {
        // Given
        Note note = new Note(1L, "Новий заголовок", "Новий контент");
        when(noteService.add(any(Note.class))).thenReturn(note);

        // When & Then
        mockMvc.perform(post("/note/create")
                .param("title", "Новий заголовок")
                .param("content", "Новий контент")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/note/list"));

        verify(noteService).add(any(Note.class));
    }

    @Test
    @WithMockUser
    void testShowEditForm() throws Exception {
        // Given
        Note note = new Note(1L, "Заголовок", "Контент");
        when(noteService.getById(1L)).thenReturn(note);

        // When & Then
        mockMvc.perform(get("/note/edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("note", note))
                .andExpect(view().name("note/edit"));
    }

    @Test
    @WithMockUser
    void testUpdateNote() throws Exception {
        // When & Then
        mockMvc.perform(post("/note/edit")
                .param("id", "1")
                .param("title", "Оновлений заголовок")
                .param("content", "Оновлений контент")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/note/list"));

        verify(noteService).update(any(Note.class));
    }

    @Test
    @WithMockUser
    void testDeleteNote() throws Exception {
        // When & Then
        mockMvc.perform(post("/note/delete")
                .param("id", "1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/note/list"));

        verify(noteService).deleteById(1L);
    }
}