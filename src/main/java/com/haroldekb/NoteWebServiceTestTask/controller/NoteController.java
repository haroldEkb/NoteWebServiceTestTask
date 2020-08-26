package com.haroldekb.NoteWebServiceTestTask.controller;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @GetMapping("/")
    public String showNotes(@RequestParam(required = false, value = "search") String search, Model model){
        List<Note> notes;
        if (search != null && !search.equals("")) {
            notes = service.searchContaining(search);
        } else {
            notes = service.getAllNotes();
        }
        model.addAttribute("notes", notes);
        return "index";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id")  Integer id){
        if (!service.doExistById(id)){
            throw new NoSuchElementException("There is no note with such id");
        }
        service.deleteNoteById(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String newNoteForm(Model model){
        model.addAttribute("note", new Note());
        return "add_note";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute(name = "note") Note newNote, HttpServletResponse response){
        if (newNote == null || newNote.isEmpty()) {
            throw new NullPointerException("Note is empty");
        }
        service.save(newNote);
        return "redirect:/";
    }

    @ExceptionHandler({NoSuchElementException.class, NullPointerException.class})
    public String handleError(Model model, HttpServletRequest request,
                              HttpServletResponse response, Exception ex) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("id", Optional.ofNullable(request.getParameter("id")).orElseGet(String::new));
        return "error";
    }
}
