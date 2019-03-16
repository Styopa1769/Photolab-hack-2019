package ru.photo.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.photo.db.model.Emotion;
import ru.photo.db.repo.EmotionRepository;

import java.util.List;

@RestController
public class EmotionWebController {

    @Autowired
    EmotionRepository repository;

    @RequestMapping("/emotions/get_all")
    @ResponseBody
    public Iterable<Emotion> getAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/emotions/add_emotion", method = RequestMethod.POST)
    public void addTeam(@RequestBody Emotion emotion){
        repository.save(emotion);
    }


    @RequestMapping(value = "/emotions/delete_emotion", method = RequestMethod.POST)
    public void deleteTeam(@RequestBody long id){
        repository.delete(repository.findOneById(id));
    }

    @RequestMapping(value = "/emotions/update_emotion", method = RequestMethod.POST)
    public void updateTeam(@RequestBody Emotion emotion){
        Emotion updatedEmotion = repository.findOneById(emotion.getId());
        updatedEmotion = emotion;
        repository.save(updatedEmotion);
    }
}
