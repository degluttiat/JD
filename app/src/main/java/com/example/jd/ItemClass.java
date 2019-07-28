package com.example.jd;

public class ItemClass {
    String textBreakfast;
    String textLunch;
    String textSupper;
    int image;



    public ItemClass(String breakfast, String lunch, String supper, int image) {
        this.textBreakfast = breakfast;
        this.textLunch = lunch;
        this.textSupper = supper;
        this.image = image;
    }

    public String getTextBreakfast() {
        return textBreakfast;
    }

    public void setTextBreakfast(String name) {
        this.textBreakfast = name;
    }

    public String getTextLunch() {
        return textLunch;
    }

    public void setTextLunch(String textLunch) {
        this.textLunch = textLunch;
    }

    public String getTextSupper() {
        return textSupper;
    }

    public void setTextSupper(String textSupper) {
        this.textSupper = textSupper;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}

