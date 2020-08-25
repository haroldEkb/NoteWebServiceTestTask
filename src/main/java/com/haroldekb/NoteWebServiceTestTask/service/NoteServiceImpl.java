package com.haroldekb.NoteWebServiceTestTask.service;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haroldekb@mail.ru
 **/

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository repository;

    @Autowired
    public NoteServiceImpl(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    @Override
    public void save(Note s) {
        repository.save(s);
    }

    @Override
    public void saveAll(List<Note> notes) {
        repository.saveAll(notes);
    }

    @Override
    public Note findNoteById(Integer id) {
        return repository.findById(id).orElse(new Note());
    }

    @Override
    public void deleteNoteById(Integer id) {
        if (doExistById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    public boolean doExistById(Integer id) {
        return repository.existsById(id);
    }
}
