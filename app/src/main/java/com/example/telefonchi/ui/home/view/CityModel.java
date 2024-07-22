package com.example.telefonchi.ui.home.view;

import java.util.List;

public class CityModel {
    private String name, nick, comment , year ;
    private int    totalSum, startSum, finishSum, amountMonth,sumMonth,  tel,  payment;

//    private List<String> regions;

    public CityModel() {

    }

    public CityModel(String name, String nick, int tel, String comment, String year, int totalSum, int startSum, int finishSum, int amountMonth, int sumMonth, int payment) {
        this.name = name;
        this.nick = nick;
        this.tel = tel;
        this.comment = comment;
        this.year = year;
        this.totalSum = totalSum;
        this.startSum = startSum;
        this.finishSum = finishSum;
        this.amountMonth = amountMonth;
        this.sumMonth = sumMonth;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public int getTel() {
        return tel;
    }

    public String getComment() {
        return comment;
    }

    public String getYear() {
        return year;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public int getStartSum() {
        return startSum;
    }

    public int getFinishSum() {
        return finishSum;
    }

    public int getAmountMonth() {
        return amountMonth;
    }

    public int getSumMonth() {
        return sumMonth;
    }

    public int getPayment() {
        return payment;
    }

    //    public List<String> getRegions() {
//        return regions;
//    }
}
