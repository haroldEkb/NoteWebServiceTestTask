package com.haroldekb.NoteWebServiceTestTask.controller;

import com.haroldekb.NoteWebServiceTestTask.NoteTestConfiguration;
import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@MockBean(NoteService.class)
@MockBean(Model.class)
@ContextConfiguration(classes = NoteTestConfiguration.class)
public class NoteControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteService service;

    @Autowired
    @Qualifier("testNotes")
    private List<Note> testNotes;

    @DisplayName("Должен вызывать метод getAllNotes у NoteService")
    @Test
    void getAllNotesWhenMainPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
        verify(service).getAllNotes();
    }

    @DisplayName("Должен возвращать клиенту список Note, передаваемый из NoteService ")
    @Test
    void showAllNotesTest() throws Exception {
        when(service.getAllNotes()).thenReturn(testNotes);
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attribute("notes", testNotes))
        ;
    }

    @DisplayName("Должен вызывать метод deleteNoteById у NoteService")
    @Test
    void deleteNoteTest() throws Exception {
        when(service.doExistById(anyInt())).thenReturn(true);
        mvc.perform(get("/delete?id=12"))
                .andExpect(status().is3xxRedirection());
        verify(service).deleteNoteById(12);
    }

    @DisplayName("Должен перенаправить на главную страницу с сообщением об успешном удалении заметки")
    @Test
    void deleteTestMessage() throws Exception {
        mvc.perform(post("/add")
                .flashAttr("note", testNotes.get(0)))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "New note is successfully added"));
    }

    @DisplayName("Должен возвращать страницу ошибки, если был передан несуществующий id")
    @Test
    void errorWhenDeleteNotExistingId() throws Exception {
        when(service.doExistById(anyInt())).thenReturn(false);
        mvc.perform(get("/delete?id=12"))
                .andExpect(status().isBadRequest())
        .andExpect(model().attribute("message", "There is no note with such id"))
        .andExpect(model().attribute("id", "12"));
    }

    @DisplayName("Должен возвращать new Note при запросе на добавление заметки")
    @Test
    void returnNewNoteWhenAddNoteGetRequest() throws Exception {
        mvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("note"));
    }

    @DisplayName("Должен вызывать метод save у NoteService")
    @Test
    void saveNoteWhenWhenAddNotePostRequest() throws Exception {
        mvc.perform(post("/add")
                .flashAttr("note", testNotes.get(0)));
        verify(service).save(any());
    }

    @DisplayName("Должен перенаправить на главную страницу с сообщением об успешном создании заметки")
    @Test
    void returnMessageWhenAddNewNote() throws Exception {
        mvc.perform(post("/add")
                .flashAttr("note", testNotes.get(0)))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "New note is successfully added"));
    }

    @DisplayName("Должен возвращать страницу ошибки, если была передана пустая заметка или null")
    @Test
    void errorWhenPostEmptyNote() throws Exception {
        mvc.perform(post("/add")
                .flashAttr("note", new Note()))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Note is empty"))
                .andExpect(model().attributeExists("id"));
    }
}
