package ru.photo.db.controller;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.photo.db.ReadString;
import ru.photo.db.model.Emotion;
import ru.photo.db.model.Meme;
import ru.photo.db.repo.EmotionRepository;
import ru.photo.db.repo.MemeRepository;

@Controller
public class MemeController {

    @Autowired
    MemeRepository memeRepository;
    @Autowired
    EmotionRepository emotionRepository;

    @RequestMapping(value="/meme", method=RequestMethod.GET)
    public @ResponseBody String page() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>\n" +
                "<head>\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"elem\"></div>\n" +
                "<form method=\"POST\" enctype=\"multipart/form-data\" action=\"/meme/upload\">\n" +
                "    File to upload:\n" +
                "    <input type=\"file\" name=\"file\">\n" +
                "    <br /> Name: <input type=\"text\" name=\"name\"><br />\n"+
                "<br />Выберите эмоцию:" +
                "<br /><select name=\"emotion\">\n");
        for (Emotion emotion: emotionRepository.findAll()){
            stringBuilder.append("<option ").append("value=\"")
                    .append(emotion.getId()).append("\">").append(emotion.getName()).append("</option>");
        }
        stringBuilder.append("</select>\n"+
                "<br /> <input type=\"submit\" value=\"Upload\">"+
                "</form>\n" +
                "</body>\n" +
                "</html>");
        return stringBuilder.toString();
    }

    @RequestMapping(value="/meme/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("emotion") String emotion){
        boolean uploaded = false;
        if (!file.isEmpty()) try {
            byte[] bytes = file.getBytes();
            File newFile = new File("memes"+File.separator+file.getOriginalFilename());
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(bytes);
            stream.close();
            uploaded = true;
            memeRepository.save(new Meme(name,newFile.getPath(),
                    emotionRepository.findOneById(Long.valueOf(emotion))));
        } catch (Exception e) {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " => " + e.getMessage();
        }
        else {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
        return "Вы удачно загрузили " + file.getOriginalFilename()+" !";
    }

}
