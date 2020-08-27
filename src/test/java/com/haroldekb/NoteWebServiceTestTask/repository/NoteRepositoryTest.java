package com.haroldekb.NoteWebServiceTestTask.repository;

import com.haroldekb.NoteWebServiceTestTask.NoteTestConfiguration;
import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * @author haroldekb@mail.ru
 **/

@DataJpaTest
@ContextConfiguration(classes = NoteTestConfiguration.class)
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository repository;

    @Autowired
    @Qualifier("testNotes")
    private List<Note> testNotes;

    @Autowired
    @Qualifier("searchNotes")
    private List<Note> searchNotes;

    @BeforeEach
    void setUp() {
        repository.saveAll(testNotes);
    }

    @Test
    void searchInNameAndContent1(){
        List<Note> notes = repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
        Assertions.assertNotNull(notes);
        Assertions.assertTrue(notes.size() != 0);
    }

    @Test
    void searchInNameAndContent2(){
        List<Note> notes = repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
        Assertions.assertEquals(3, notes.size());
    }

    @Test
    void searchInNameAndContent3(){
        List<Note> notes = repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
        boolean flag = true;
        for (Note note : notes) {
            System.out.println(note);
        }
        for (Note searchNote : searchNotes) {
            System.out.println(searchNote);
            if (!notes.contains(searchNote)) {
                flag = false;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }


}
