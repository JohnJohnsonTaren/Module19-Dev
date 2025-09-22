// Module16-Dev
//  Зміни сервіс NoteService, щоб зберігати нотатки в БД, замість того, щоб зберігати їх в колеції як це було реалізовано раніше.
//

package com.example.service;

import com.example.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> listAll();

    Note add(Note note);

    void deleteById(long id);

    Note update(Note note);

    Note getById(long id);
}
