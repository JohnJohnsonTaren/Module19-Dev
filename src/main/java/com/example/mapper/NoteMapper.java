package com.example.mapper;

import com.example.dto.NoteRequestDto;
import com.example.dto.NoteResponseDto;
import com.example.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
// Відповідає за перетворення між сутністю Note та її DTO-версіями
public class NoteMapper {
    public Note toEntity(NoteRequestDto dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return note;
    }

    public Note toEntity(Long id, NoteRequestDto dto) {
        Note note = new Note();
        note.setId(id);
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return note;
    }

    public NoteResponseDto toDto(Note note) {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        return dto;
    }

    public List<NoteResponseDto> toDtoList(List<Note> notes) {
        return notes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
