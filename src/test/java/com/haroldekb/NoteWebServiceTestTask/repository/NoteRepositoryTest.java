package com.haroldekb.NoteWebServiceTestTask.repository;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.repository.NoteRepository;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haroldekb@mail.ru
 **/

@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository repository;

    private List<Note> testNotes = new ArrayList<>();
    private List<Note> searchNotes = new ArrayList<>();

    @BeforeEach
    public void setup(){
        repository.saveAll(initTestNotes());
        initSearchNotes();
    }

    @Test
    void searchInNameAndContent1(){
        List<Note> notes = repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
        Assert.assertNotNull(notes);
    }

    @Test
    void searchInNameAndContent2(){
        List<Note> notes = repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
        Assert.assertEquals(3, notes.size());
    }

    @Test
    void searchInNameAndContent3(){
        List<Note> notes = repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
        boolean flag = true;
        for (Note searchNote : searchNotes) {
            flag = notes.contains(searchNote);
        }
        Assert.assertTrue(flag);
    }

    private List<Note> initTestNotes(){
        testNotes.add(new Note(1, "xvxcv123tgttg", "thh123qweqwe"));
        testNotes.add(new Note(2, "456", "asdsafd"));
        testNotes.add(new Note(3, "wqe123qwe", "34435xvcxcxvvcx"));
        testNotes.add(new Note(4, "", "123xvcxcxvvcx"));
        return testNotes;
    }

    private List<Note> initSearchNotes(){
        searchNotes.add(new Note(1, "123", "123qweqwe"));
        searchNotes.add(new Note(2, "456", "asdsafd"));
        searchNotes.add(new Note(3, "wqe123qwe", "34435xvcxcxvvcx"));
        searchNotes.add(new Note(4, "", "123xvcxcxvvcx"));
        return searchNotes;
    }
}
