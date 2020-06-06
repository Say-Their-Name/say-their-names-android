package com.blm.saytheirnames.models;

import java.util.List;

public class Petition {

    int id;
    String title, description, outcome,link,image_url;
    Person person;
    List<Images> images;
    Type type;

    public Petition(int id, String title, String description, String outcome,String link, String image_url,Person person, List<Images> images,Type type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.outcome = outcome;
        this.link = link;
        this.image_url = image_url;
        this.person = person;
        this.images = images;
        this.type = type;
    }


    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Person getPerson() {
        return person;
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

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}
