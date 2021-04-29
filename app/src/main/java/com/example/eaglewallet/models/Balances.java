package com.example.eaglewallet.models;

import java.io.Serializable;

public class Balances implements Serializable {
    int userId, mealPlans;
    double sodexoBucks, diningDollars, eagleDollars;

    public double getEagleDollars() {
        return eagleDollars;
    }

    public double getDiningDollars() {
        return diningDollars;
    }

    public double getSodexoBucks() {
        return sodexoBucks;
    }

    public int getMealPlans() {
        return mealPlans;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDiningDollars(double diningDollars) {
        this.diningDollars = diningDollars;
    }

    public void setMealPlans(int mealPlans) {
        this.mealPlans = mealPlans;
    }

    public void setEagleDollars(double eagleDollars) {
        this.eagleDollars = eagleDollars;
    }

    public void setSodexoBucks(double sodexoBucks) {
        this.sodexoBucks = sodexoBucks;
    }
}
