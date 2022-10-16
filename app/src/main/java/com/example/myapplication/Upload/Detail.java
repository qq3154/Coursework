package com.example.myapplication.Upload;

import com.example.myapplication.Expense.Expense;

import java.util.List;

public class Detail {
    private int id;
    private String name;
    private String destination;
    private String date;
    private Boolean riskAssessment;
    private String description;
    private List<Expense> expenses;

    public Detail(int id, String name, String destination, String date, Boolean riskAssessment, String description, List<Expense> expenses) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.riskAssessment = riskAssessment;
        this.description = description;
        this.expenses = expenses;
    }
}
