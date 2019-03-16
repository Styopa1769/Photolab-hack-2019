package ru.photo.db.model;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "memes")
public class Meme {

    @Id @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "filepath")
    private String filepath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emotion_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Emotion emotion;

    public Meme(String name, String filepath, Emotion emotion) {
        this.name = name;
        this.filepath = filepath;
        this.emotion = emotion;
    }
}
