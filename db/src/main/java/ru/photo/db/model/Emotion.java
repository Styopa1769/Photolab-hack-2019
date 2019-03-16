package ru.photo.db.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "emotions")
public class Emotion implements Serializable {

    @Id @GeneratedValue
    private long id;

    @Column (name = "name")
    private String name;

    public Emotion(){}

    public Emotion(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
