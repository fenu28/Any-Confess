package com.fenil.hackathon.Model;

import java.util.ArrayList;

public class CreatePostRequest {

    String identity;
    String text;
    ArrayList<String> hashtags;

    public CreatePostRequest(String identity, String text, ArrayList<String> hashtags) {
        this.identity = identity;
        this.text = text;
        this.hashtags = hashtags;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }
}
