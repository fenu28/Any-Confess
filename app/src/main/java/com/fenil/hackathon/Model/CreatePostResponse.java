package com.fenil.hackathon.Model;

public class CreatePostResponse {

    String message;
    Post post;

    public CreatePostResponse() {
    }

    public CreatePostResponse(String message, Post post) {
        this.message = message;
        this.post = post;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
