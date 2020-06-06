package com.blm.saytheirnames.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Person {

    @SerializedName("id")
    String id;

    @SerializedName("full_name")
    String fullName;

    @SerializedName("identifier")
    String identifier;

    @SerializedName("date_of_birth")
    String dateOfBirth; //TODO: This is always null currently, we'll need to update this to a correct type eventually

    @SerializedName("date_of_incident")
    String dateOfIncident;

    @SerializedName("number_of_children")
    Integer numberOfChildren;

    @SerializedName("age")
    Integer age;

    @SerializedName("city")
    String city;

    @SerializedName("country")
    String country;

    @SerializedName("their_story")
    String theirStory;

    @SerializedName("outcome")
    String outcome;

    @SerializedName("context")
    String context;

    @SerializedName("images")
    List<Images> images;

    @SerializedName("donation_links")
    List<Donation> donationLinks;

    @SerializedName("petition_links")
    List<Petition> petitionLinks;

    @SerializedName("media_links")
    List<Media> mediaLinks;

    @SerializedName("hash_tags")
    List<Hashtag> hashtags;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(String dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTheirStory() {
        return theirStory;
    }

    public void setTheirStory(String theirStory) {
        this.theirStory = theirStory;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Donation> getDonationLinks() {
        return donationLinks;
    }

    public void setDonationLinks(List<Donation> donationLinks) {
        this.donationLinks = donationLinks;
    }

    public List<Petition> getPetitionLinks() {
        return petitionLinks;
    }

    public void setPetitionLinks(List<Petition> petitionLinks) {
        this.petitionLinks = petitionLinks;
    }

    public List<Media> getMediaLinks() {
        return mediaLinks;
    }

    public void setMediaLinks(List<Media> mediaLinks) {
        this.mediaLinks = mediaLinks;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtag) {
        this.hashtags = hashtag;
    }
}
