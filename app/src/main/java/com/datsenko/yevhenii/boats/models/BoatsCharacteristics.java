package com.datsenko.yevhenii.boats.models;

import java.util.ArrayList;

/**
 * Created by Женя on 29.06.2016.
 */
public class BoatsCharacteristics {
    private String abbreviation;
    private String name;
    private String characteristics;
    private String pictures;
    private String send_mail;
    private String videos;
    private ArrayList<FieldCharacteristics> characteristicsArrayList;

    public BoatsCharacteristics(String abbreviation, String name, String characteristics, String pictures, String send_mail, String videos) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.characteristics = characteristics;
        this.pictures = pictures;
        this.send_mail = send_mail;
        this.videos = videos;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getSend_mail() {
        return send_mail;
    }

    public void setSend_mail(String send_mail) {
        this.send_mail = send_mail;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FieldCharacteristics> getCharacteristicsArrayList() {
        return characteristicsArrayList;
    }

    public void setCharacteristicsArrayList(ArrayList<FieldCharacteristics> characteristicsArrayList) {
        this.characteristicsArrayList = characteristicsArrayList;
    }

    @Override
    public String toString() {
        return "BoatsCharacteristics{" +
                "abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", characteristicsArrayList=" + characteristicsArrayList +
                '}';
    }
}
