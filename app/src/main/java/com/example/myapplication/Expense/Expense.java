package com.example.myapplication.Expense;

public class Expense {

    private int id;
    private int tripId;
    private String type;
    private Float amount;
    private String date;

    public Expense(int id, int tripId, String type, Float amount, String date) {
        this.id = id;
        this.tripId = tripId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}