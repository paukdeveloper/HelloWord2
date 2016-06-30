package com.datsenko.yevhenii.boats.models;

import java.util.ArrayList;

/**
 * Created by Женя on 29.06.2016.
 */
public class BoatsCharacteristics {
    private String abbreviation;
    private String name;
    private ArrayList<FieldCharacteristics> characteristicsArrayList;

    public BoatsCharacteristics(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
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
