package com.datsenko.yevhenii.boats.models;

/**
 * Created by Женя on 29.06.2016.
 */
public class FieldCharacteristics {
    private String fieldLabel;
    private String fieldValue;

    public FieldCharacteristics(String fieldLabel, String fieldValue) {
        this.fieldLabel = fieldLabel;
        this.fieldValue = fieldValue;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return "FieldCharacteristics{" +
                "fieldLabel='" + fieldLabel + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                '}';
    }
}
