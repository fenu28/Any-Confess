package com.fenil.hackathon.Services;

import com.fenil.hackathon.Model.AddCommentRequest;
import com.fenil.hackathon.Model.AddCommentResponse;
import com.fenil.hackathon.Model.CreatePostRequest;
import com.fenil.hackathon.Model.CreatePostResponse;
import com.fenil.hackathon.Model.GetCommentsResponse;
import com.fenil.hackathon.Model.PostsApiResponse;
import com.fenil.hackathon.Model.PostsFetchRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("post/by-date")
    Call<ArrayList<PostsApiResponse>> getAllContestsFromApi(@Body PostsFetchRequest postsFetchRequest);

    @POST("post/add")
    Call<CreatePostResponse> addPost(@Body CreatePostRequest createPostRequest);

    @POST("comment/add/{postId}")
    Call<AddCommentResponse> addComment(@Path("postId") String postid, @Body AddCommentRequest addCommentRequest);

    @GET("comment/get/{postId}")
    Call<GetCommentsResponse> getComment(@Path("postId") String postid);

}