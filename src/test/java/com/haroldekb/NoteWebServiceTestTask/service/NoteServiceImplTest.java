package com.haroldekb.NoteWebServiceTestTask.service;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.repository.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = NoteServiceTestConfiguration.class)
public class NoteServiceImplTest {

    @Mock
    private NoteRepository repository;

    @Autowired
    @Qualifier("Note")
    private Note note;

    @Autowired
    @Qualifier("testNotes")
    private List<Note> testNotes;

    private NoteService service;

    @BeforeEach
    void setUp() {
        service = new NoteServiceImpl(repository);
    }

    @Test
    void getAllNotesTest() {
        service.getAllNotes();
        verify(repository).findAll();
    }

    @Test
    void getAllNotesTest1() {
        when(repository.findAll()).thenReturn(testNotes);
        Assertions.assertSame(service.getAllNotes(), testNotes);
    }

    @Test
    void saveTest() {
        service.save(note);
        verify(repository).save(any());
    }

    @Test
    void saveAllTest() {
        service.saveAll(testNotes);
        verify(repository).saveAll(any());
    }

    @Test
    void findNoteTest() {
        service.findNoteById(anyInt());
        verify(repository).findById(31);
    }

    @Test
    void findNoteTest1() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(note));
        service.findNoteById(anyInt());
        Assertions.assertSame(service.findNoteById(25), note);
    }

    @Test
    void doExistsTest() {
        service.doExistById(anyInt());
        verify(repository).existsById(12);
    }

    @Test
    void doExists1() {
        when(repository.existsById(anyInt())).thenReturn(true);
        Assertions.assertTrue(service.doExistById(12));
    }

    @Test
    void deleteTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        service.deleteNoteById(13);
        verify(repository).deleteById(anyInt());
    }

    @Test
    void searchContainingTest() {
        service.searchContaining("123");
        verify(repository).findByNameContainingIgnoreCaseOrContentContainingIgnoreCase("123", "123");
    }

    @Test
    void searchContainingTest1() {
        when(repository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(any(), any())).thenReturn(testNotes);
        Assertions.assertSame(service.searchContaining("123"), testNotes);
    }
}
