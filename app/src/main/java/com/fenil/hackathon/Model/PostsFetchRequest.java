package com.fenil.hackathon.Model;

public class PostsFetchRequest {

    String date;

    public PostsFetchRequest(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
