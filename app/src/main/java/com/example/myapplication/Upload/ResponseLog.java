package com.example.myapplication.Upload;

public class ResponseLog {
    private int id;
    private String date;
    private String description;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id);
        sb.append("\n");
        sb.append("date: " + date);
        sb.append("\n");
        sb.append("description: " + description);
        sb.append("\n");
        return sb.toString();

    }

    public ResponseLog(int id, String date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
