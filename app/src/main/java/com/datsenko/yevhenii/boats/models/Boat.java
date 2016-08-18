package com.datsenko.yevhenii.boats.models;

import java.util.ArrayList;

/**
 * Created by Женя on 29.06.2016.
 */
public class Boat {
    private String id;
    private String name;
    private String graph;
    private String web_link;
    private ArrayList<String> arrayImages;
    private ArrayList<String> arrayVideos;
    private ArrayList<BoatsCharacteristics> characteristicsArrayList;

    public Boat(String id, String name, String graph, String web_link) {
        this.id = id;
        this.name = name;
        this.graph = graph;
        this.web_link = web_link;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
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
        } else {
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
