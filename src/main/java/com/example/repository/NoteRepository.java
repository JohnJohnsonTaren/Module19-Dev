// Напиши репозиторій NoteRepository для роботи з сутностями типу Note

package com.example.repository;

import com.example.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
