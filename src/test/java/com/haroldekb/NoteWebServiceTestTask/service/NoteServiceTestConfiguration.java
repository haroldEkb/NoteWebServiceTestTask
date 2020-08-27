package com.haroldekb.NoteWebServiceTestTask.service;

import com.haroldekb.NoteWebServiceTestTask.entity.Note;
import com.haroldekb.NoteWebServiceTestTask.NoteTestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(NoteTestConfiguration.class)
public class NoteServiceTestConfiguration {

    @Bean("Note")
    public Note note(){
        return new Note(1, "xvxcv123tgttg", "thh123qweqwe");
    }
}
