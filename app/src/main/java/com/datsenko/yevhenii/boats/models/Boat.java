package com.datsenko.yevhenii.boats.models;

import java.util.ArrayList;

/**
 * Created by Женя on 29.06.2016.
 */
public class Boat {
    private String id;
    private String name;
    private ArrayList<String> arrayImages;
    private ArrayList<String> arrayVideos;
    private ArrayList<BoatsCharacteristics> characteristicsArrayList;

    public Boat(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getArrayImages() {
        return arrayImages;
    }

    public void setArrayImages(ArrayList<String> arrayImages) {
        this.arrayImages = arrayImages;
    }

    public ArrayList<String> getArrayVideos() {
        return arrayVideos;
    }

    public void setArrayVideos(ArrayList<String> arrayVideos) {
        this.arrayVideos = arrayVideos;
    }

    public ArrayList<BoatsCharacteristics> getCharacteristicsArrayList() {
        return characteristicsArrayList;
    }

    public void setCharacteristicsArrayList(ArrayList<BoatsCharacteristics> characteristicsArrayList) {
        this.characteristicsArrayList = characteristicsArrayList;
    }

    public void addCharacteristicsArrayList(BoatsCharacteristics characteristicsArrayList) {
        if (this.characteristicsArrayList == null) {
            this.characteristicsArrayList = new ArrayList<>();
            this.characteristicsArrayList.add(characteristicsArrayList);
        }
    }

    @Override
    public String toString() {
        return "Boat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", arrayImages=" + arrayImages +
                ", arrayVideos=" + arrayVideos +
                ", characteristicsArrayList=" + characteristicsArrayList +
                '}';
    }
}
