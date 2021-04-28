package com.example.eaglewallet.models;

import java.io.Serializable;

public class Transaction implements Serializable {
    int transId, userId;
    double sodexoBucks, diningDollars, eagleDollars, mealPlans;
    String date;

    public int getUserId() {
        return userId;
    }

    public int getTransId() {
        return transId;
    }

    public double getSodexoBucks() {
        return sodexoBucks;
    }

    public double getDiningDollars() {
        return diningDollars;
    }

    public double getEagleDollars() {
        return eagleDollars;
    }

    public double getMealPlans() {
        return mealPlans;
    }

    public String getDate() {
        return date;
    }

    public void setSodexoBucks(double sodexoBucks) {
        this.sodexoBucks = sodexoBucks;
    }

    public void setEagleDollars(double eagleDollars) {
        this.eagleDollars = eagleDollars;
    }

    public void setMealPlans(double mealPlans) {
        this.mealPlans = mealPlans;
    }

    public void setDiningDollars(double diningDollars) {
        this.diningDollars = diningDollars;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
