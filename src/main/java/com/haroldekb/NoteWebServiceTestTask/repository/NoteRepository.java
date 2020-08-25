package com.haroldekb.NoteWebServiceTestTask.repository;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(String search, String search1);
}
