package com.example.eaglewallet.models;

import java.io.Serializable;

public class Balances implements Serializable {
    int userId, mealPlans;
    double sodexoBucks, diningDollars, eagleDollars;

    public int getMealPlans() {
        return mealPlans;
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

    public int getUserId() {
        return userId;
    }

    public void setSodexoBucks(double sodexoBucks) {
        this.sodexoBucks = sodexoBucks;
    }

    public void setEagleDollars(double eagleDollars) {
        this.eagleDollars = eagleDollars;
    }

    public void setMealPlans(int mealPlans) {
        this.mealPlans = mealPlans;
    }

    public void setDiningDollars(double diningDollars) {
        this.diningDollars = diningDollars;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
