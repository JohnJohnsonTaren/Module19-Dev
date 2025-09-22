package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// Використовуватися для отримання даних від клієнта під час створення або оновлення нотатка
public class NoteRequestDto {
    @NotBlank(message = "Назва не можке бути порожньою")
    @Size(min = 3, max = 100, message = "Назва повинна бути від 3 до 100 символів")
    private String title;
    @NotBlank(message = "Зміст не може бути порожнім")
    @Size(min = 5, max = 5000, message = "Зміст повинен бути від 5 да 5000 символів")
    private String content;
}
