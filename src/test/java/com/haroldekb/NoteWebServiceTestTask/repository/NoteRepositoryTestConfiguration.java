package com.haroldekb.NoteWebServiceTestTask.repository;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class NoteRepositoryTestConfiguration {

    @Bean("testNotes")
    public List<Note> testNotes(){
        List<Note> testNotes = new ArrayList<>();
        testNotes.add(new Note(1, "xvxcv123tgttg", "thh123qweqwe"));
        testNotes.add(new Note(2, "456", "asdsafd"));
        testNotes.add(new Note(3, "wqe123qwe", "34435xvcxcxvvcx"));
        testNotes.add(new Note(4, "", "123xvcxcxvvcx"));
        return testNotes;
    }

    @Bean("searchNotes")
    public List<Note> searchNotes(){
        List<Note> searchNotes = new ArrayList<>();
        searchNotes.add(new Note(1, "xvxcv123tgttg", "thh123qweqwe"));
        searchNotes.add(new Note(3, "wqe123qwe", "34435xvcxcxvvcx"));
        searchNotes.add(new Note(4, "", "123xvcxcxvvcx"));
        return searchNotes;
    }
}
