package com.example.myapplication.Trip;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private String name;
    private String destination;
    private String date;
    private Boolean riskAssessment;
    private String description;

    public Trip(int id, String name, String destination, String date, Boolean riskAssessment, String description) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.riskAssessment = riskAssessment;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getRiskAssessment() {
        return riskAssessment;
    }

    public void setRiskAssessment(Boolean riskAssessment) {
        this.riskAssessment = riskAssessment;
    }



}
