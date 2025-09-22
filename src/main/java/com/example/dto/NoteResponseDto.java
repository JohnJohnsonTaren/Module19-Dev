package com.example.dto;

import lombok.Data;

@Data
// Використовуватися для відправки даних клієнту
public class NoteResponseDto {
    private Long id;
    private String title;
    private String content;
}
