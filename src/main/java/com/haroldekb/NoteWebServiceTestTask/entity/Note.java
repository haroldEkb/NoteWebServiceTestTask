package com.haroldekb.NoteWebServiceTestTask.entity;

import javax.persistence.*;

/**
 * @author haroldekb@mail.ru
 **/

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    public Note() {
    }

    public Note(Integer id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
