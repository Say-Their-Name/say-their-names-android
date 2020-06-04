package com.blm.saytheirnames.models;

import java.util.List;

public class Petition {

    int id;
    String title, description, link;
    Person person;
    List<Images> images;
    Type type;

    public Petition(int id, String title, String description, String link, Person person, List<Images> images,Type type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.person = person;
        this.images = images;
        this.type = type;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
/*
    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }*/

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
}
