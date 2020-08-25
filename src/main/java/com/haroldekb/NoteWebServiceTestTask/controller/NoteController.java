package com.haroldekb.NoteWebServiceTestTask.controller;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        notes.add(new Note("", "xvcxcxvvcx"));
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

    @GetMapping("/add")
    public String newNoteForm(Model model){
        model.addAttribute("note", new Note());
        return "add_note";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute(name = "note") Note newNote){
        service.save(newNote);
        return "redirect:/";
    }
}
