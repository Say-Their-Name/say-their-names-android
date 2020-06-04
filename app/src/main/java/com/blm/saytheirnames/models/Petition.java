package com.blm.saytheirnames.models;

public class Petition {

    int id;
    String title, description, link,person;
    String[] images;

    public Petition(int id, String title, String description, String link, String person, String[] images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.person = person;
        this.images = images;
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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
