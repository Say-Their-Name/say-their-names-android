package io.saytheirnames.models;

public class Donation {

    int id;
    String identifier,title, description, link, outcome,outcome_img_url,banner_img_url;
    Person person;
    Type type;

    public Donation(int id, String identifier, String title, String description, String link, String outcome, String outcome_img_url, String banner_img_url, Person person, Type type) {
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.link = link;
        this.outcome = outcome;
        this.outcome_img_url = outcome_img_url;
        this.banner_img_url = banner_img_url;
        this.person = person;
        this.type = type;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
