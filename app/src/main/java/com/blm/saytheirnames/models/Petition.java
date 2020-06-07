package com.blm.saytheirnames.models;

import java.util.List;

public class Petition {

    int id;
    String identifier, title, description, outcome, link, outcome_img_url, banner_img_url;
    Person person;
    List<Images> images;
    Type type;
    Hashtag hashtag;

    public Petition(int id, String identifier, String title, String description, String outcome, String link, String outcome_img_url, String banner_img_url, Person person, List<Images> images, Type type, Hashtag hashtag) {
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.outcome = outcome;
        this.link = link;
        this.outcome_img_url = outcome_img_url;
        this.banner_img_url = banner_img_url;
        this.person = person;
        this.images = images;
        this.type = type;
        this.hashtag = hashtag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOutcome_img_url() {
        return outcome_img_url;
    }

    public void setOutcome_img_url(String outcome_img_url) {
        this.outcome_img_url = outcome_img_url;
    }

    public String getBanner_img_url() {
        return banner_img_url;
    }

    public void setBanner_img_url(String banner_img_url) {
        this.banner_img_url = banner_img_url;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Hashtag getHashtag() {
        return hashtag;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }
}
