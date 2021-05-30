package com.fenil.hackathon.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetCommentsResponse {

    @SerializedName("postlist")
    @Expose
    private ArrayList<CommentResponse> postlist = null;

    public ArrayList<CommentResponse> getPostlist() {
        return postlist;
    }

    public void setPostlist(ArrayList<CommentResponse> postlist) {
        this.postlist = postlist;
    }
}
