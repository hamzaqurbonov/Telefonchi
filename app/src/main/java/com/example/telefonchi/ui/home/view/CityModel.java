package com.example.telefonchi.ui.home.view;

public class CityModel {
    private String month;
    private String id;
    private String name;
    private String sum;

//    private ArrayList<HashMap> tag;

    public CityModel(String name, String sum, String id) {
        this.name = name;
        this.sum = sum;
        this.id = id;
//        this.month = month;
    }

//    public String getMonth() {
//        return month;
//    }

    public String getName() {
        return name;
    }

    public String getSum() {
        return sum;
    }

    public String getId() {
        return id;
    }
}
