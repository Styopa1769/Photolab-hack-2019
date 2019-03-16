package ru.photo.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.photo.db.model.Emotion;
import ru.photo.db.repo.EmotionRepository;

@RestController
public class EmotionWebController {

    @Autowired
    EmotionRepository repository;
    @RequestMapping("/schedule/get_all")
    @ResponseBody
    public Iterable<Emotion> getAll(){
        return repository.findAll();
    }
}
