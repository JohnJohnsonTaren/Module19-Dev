package com.example.mapper;

import com.example.dto.NoteRequestDto;
import com.example.dto.NoteResponseDto;
import com.example.entity.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class NoteMapperTest {

    @InjectMocks
    private NoteMapper noteMapper;

    @Test
    void toEntity_convertsRequestDtoToEntity_successfully() {
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setTitle("Test Title");
        requestDto.setContent("Test Content");

        Note note = noteMapper.toEntity(requestDto);

        assertNotNull(note);
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test Content", note.getContent());
    }

    @Test
    void toEntity_withExistingId_convertsRequestDtoToEntity_successfully() {
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setContent("Updated Content");
        Long noteId = 1L;

        Note note = noteMapper.toEntity(noteId, requestDto);

        assertNotNull(note);
        assertEquals(noteId, note.getId());
        assertEquals("Updated Title", note.getTitle());
        assertEquals("Updated Content", note.getContent());
    }

    @Test
    void toDto_convertsEntityToResponseDto_successfully() {
        Note note = new Note(1L, "My Note", "Some content");

        NoteResponseDto responseDto = noteMapper.toDto(note);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
        assertEquals("My Note", responseDto.getTitle());
        assertEquals("Some content", responseDto.getContent());
    }

    @Test
    void toDtoList_convertsListOfEntitiesToListOfDtos_successfully() {
        List<Note> notes = Arrays.asList(
                new Note(1L, "Note 1", "Content 1"),
                new Note(2L, "Note 2", "Content 2")
        );

        List<NoteResponseDto> dtoList = noteMapper.toDtoList(notes);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        NoteResponseDto dto1 = dtoList.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Note 1", dto1.getTitle());

        NoteResponseDto dto2 = dtoList.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Note 2", dto2.getTitle());
    }
}