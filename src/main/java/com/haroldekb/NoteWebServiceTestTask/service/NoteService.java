package com.haroldekb.NoteWebServiceTestTask.service;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();

    void save(Note note);

    void saveAll(List<Note> notes);
}