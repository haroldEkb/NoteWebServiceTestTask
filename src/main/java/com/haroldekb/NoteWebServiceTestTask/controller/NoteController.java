package com.haroldekb.NoteWebServiceTestTask.controller;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haroldekb@mail.ru
 **/

@Controller
public class NoteController {

    private final NoteService service;

    @Autowired
    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostConstruct
    private void init(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("123", "qweqwe"));
        notes.add(new Note("456", "asdsafd"));
        notes.add(new Note("789", "xvcxcxvvcx"));
        service.saveAll(notes);
    }

    @GetMapping("/")
    public String showAllNotes(Model model){
        List<Note> notes = service.getAllNotes();
        model.addAttribute("notes", notes);
        return "index";
    }

    //Нужно ли сообщать клиенту, что такой записи не было?
    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id")  Integer id, Model model){
        service.deleteNoteById(id);
        return "redirect:/";
    }
}
