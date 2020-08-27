package com.haroldekb.NoteWebServiceTestTask.controller;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String showNotes(@RequestParam(required = false, value = "search") String search, Model model, @ModelAttribute("message") String message){
        List<Note> notes;
        if (search != null && !search.equals("")) {
            notes = service.searchContaining(search);
        } else {
            notes = service.getAllNotes();
        }
        model.addAttribute("notes", notes);
        if (message != null) model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id")  Integer id, HttpServletResponse response, RedirectAttributes attributes){
        if (!service.doExistById(id)){
            throw new NoSuchElementException("There is no note with such id");
        }
        service.deleteNoteById(id);
        attributes.addAttribute("message", "Note is successfully deleted");
        return "redirect:/";
    }

    @GetMapping("/add")
    public String newNoteForm(Model model){
        model.addAttribute("note", new Note());
        return "add_note";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute(name = "note") Note newNote, RedirectAttributes attributes){
        if (newNote == null || newNote.isEmpty()) {
            throw new NullPointerException("Note is empty");
        }
        service.save(newNote);
        attributes.addAttribute("message", "New note is successfully added");
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
