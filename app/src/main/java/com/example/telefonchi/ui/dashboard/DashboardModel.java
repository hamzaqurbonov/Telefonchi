package com.example.telefonchi.ui.dashboard;

public class DashboardModel {


    private String name, nick, comment , year ;
    private Integer    totalSum, startSum, finishSum, amountMonth,sumMonth,  tel,  payment;

//    private List<String> regions;

//    public DashboardModel() {
//
//    }


    public DashboardModel(String name, String nick, String comment, String year, Integer totalSum, Integer startSum, Integer finishSum, Integer amountMonth, Integer sumMonth, Integer tel, Integer payment) {
        this.name = name;
        this.nick = nick;
        this.comment = comment;
        this.year = year;
        this.totalSum = totalSum;
        this.startSum = startSum;
        this.finishSum = finishSum;
        this.amountMonth = amountMonth;
        this.sumMonth = sumMonth;
        this.tel = tel;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public String getComment() {
        return comment;
    }

    public String getYear() {
        return year;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public Integer getStartSum() {
        return startSum;
    }

    public Integer getFinishSum() {
        return finishSum;
    }

    public Integer getAmountMonth() {
        return amountMonth;
    }

    public Integer getSumMonth() {
        return sumMonth;
    }

    public Integer getTel() {
        return tel;
    }

    public Integer getPayment() {
        return payment;
    }
}
