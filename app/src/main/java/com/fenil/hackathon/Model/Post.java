package com.fenil.hackathon.Model;

import java.util.ArrayList;

public class Post{
    String content;
    int commentCount;
    ArrayList<String> comments;

    public Post(){

    }

    public Post(String content)
    {
        this.content = content;
        commentCount = 0;
        comments = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
}
