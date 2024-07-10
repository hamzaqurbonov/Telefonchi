package com.example.telefonchi.ui.home.view;

public class CityModel {
    private String name;
    private String sum;

    public CityModel(String name, String sum) {
        this.name = name;
        this.sum = sum;
    }
    public CityModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
