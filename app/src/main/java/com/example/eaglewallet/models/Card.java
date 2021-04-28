package com.example.eaglewallet.models;

public class Card {
    int userId;
    String cardNumber, cvc, expirationDate, fullName, streetAddress, city, state, zipCode;

    public int getUserId() {
        return userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCvc() {
        return cvc;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getFullName() {
        return fullName;
    }

    public String getState() {
        return state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
