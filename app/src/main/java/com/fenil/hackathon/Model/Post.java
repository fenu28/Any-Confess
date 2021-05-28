package com.fenil.hackathon.Model;

import java.util.ArrayList;

public class Post{
    String title;
    String text;
    int likes;
    int dislikes;
    int commentCount;
    ArrayList<String> comments;

    public Post(){

    }

    public Post(String title, String text)
    {
        this.title = title;
        this.text = text;
        likes = 0;
        dislikes = 0;
        commentCount = 0;
        comments = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount() {
        this.commentCount = comments.size();
    }
}
