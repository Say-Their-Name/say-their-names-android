package com.blm.saytheirnames.models;

public class Donation {

    String title, description, link, outcome_img_url, banner_img_url;
    ;

    public Donation(String outcome_img_url, String banner_img_url, String title, String description, String link) {
        this.outcome_img_url = outcome_img_url;
        this.banner_img_url = banner_img_url;
        this.title = title;
        this.description = description;
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
}
