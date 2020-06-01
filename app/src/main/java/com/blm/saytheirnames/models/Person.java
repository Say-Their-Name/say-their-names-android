package com.blm.saytheirnames.models;

public class Person {

    String id, fullName, date,context,bio,location;
    String[] donations, media, petitions;
    int age,childrenCount;

    public Person(String id, String fullName, String date, String context, String bio, String location, String[] donations, String[] media, String[] petitions, int age, int childrenCount) {
        this.id = id;
        this.fullName = fullName;
        this.date = date;
        this.context = context;
        this.bio = bio;
        this.location = location;
        this.donations = donations;
        this.media = media;
        this.petitions = petitions;
        this.age = age;
        this.childrenCount = childrenCount;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getDonations() {
        return donations;
    }

    public void setDonations(String[] donations) {
        this.donations = donations;
    }

    public String[] getMedia() {
        return media;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }

    public String[] getPetitions() {
        return petitions;
    }

    public void setPetitions(String[] petitions) {
        this.petitions = petitions;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}
