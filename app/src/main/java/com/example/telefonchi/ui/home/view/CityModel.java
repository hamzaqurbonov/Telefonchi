package com.example.telefonchi.ui.home.view;

public class CityModel {
    private String name, nickname, telephone, comment, year;
    private int    sum, startSum, finishSum, amountMonth,sumMonth;

    public CityModel() {

    }

    public CityModel(String name, int  sum) {
        this.name = name;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int  sum) {
        this.sum = sum;
    }
}
