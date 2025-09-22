// Module15-Dev
//  Створи сутність com.example.entity.Note - нотатка.
//      В цій сутності мають бути наступні поля:
//          id - унікальний ідентифікатор, ціле автогенероване число
//          title - назва нотатки. Рядок (String).
//          content - контент нотатки. Рядок (String).
//  Оскільки в проєкті поки немає підключеного модулю Spring Data, не додавай до сутності анотацію @Entity.

// Додай анотацію @Entity до сутності Note

package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Назва не може бути порожньою")
    @Size(min = 3, max = 100, message = "Назва повинна бути від 3 до 100 символів")
    private String title;

    @NotBlank(message = "Зміст не може бути порожнім")
    @Size(min = 5, max = 5000, message = "Зміст повинен бути від 5 до 5000 символів")
    private String content;

    public Note() {
    }

    public Note(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "com.example.entity.Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
