package com.fenil.hackathon.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCommentResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("createdPost")
    @Expose
    private CommentResponse createdPost;

    public AddCommentResponse(String message, CommentResponse createdPost) {
        this.message = message;
        this.createdPost = createdPost;
    }

    public AddCommentResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentResponse getCreatedPost() {
        return createdPost;
    }

    public void setCreatedPost(CommentResponse createdPost) {
        this.createdPost = createdPost;
    }
}
