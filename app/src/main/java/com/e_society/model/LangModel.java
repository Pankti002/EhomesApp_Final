package com.e_society.model;

public class LangModel {
    String strData;
    int imgData;

    public LangModel(String strData, int imgData) {
        this.strData = strData;
        this.imgData = imgData;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public int getImgData() {
        return imgData;
    }

    public void setImgData(int imgData) {
        this.imgData = imgData;
    }
}
