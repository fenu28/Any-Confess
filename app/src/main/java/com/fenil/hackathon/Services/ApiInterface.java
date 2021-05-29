package com.fenil.hackathon.Services;

import com.fenil.hackathon.Model.CreatePostRequest;
import com.fenil.hackathon.Model.CreatePostResponse;
import com.fenil.hackathon.Model.PostsApiResponse;
import com.fenil.hackathon.Model.PostsFetchRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("post/by-date")
    Call<ArrayList<PostsApiResponse>> getAllContestsFromApi(@Body PostsFetchRequest postsFetchRequest);

    @POST("post/add")
    Call<CreatePostResponse> addPost(@Body CreatePostRequest createPostRequest);

}