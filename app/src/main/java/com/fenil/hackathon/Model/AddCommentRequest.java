package com.fenil.hackathon.Model;

public class AddCommentRequest {

    String identity;
    String text;

    public AddCommentRequest() {
    }

    public AddCommentRequest(String identity, String text) {
        this.identity = identity;
        this.text = text;
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
}
