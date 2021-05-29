package com.fenil.hackathon.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fenil.hackathon.Model.CreatePostRequest;
import com.fenil.hackathon.Model.CreatePostResponse;
import com.fenil.hackathon.Model.Post;
import com.fenil.hackathon.Repos.CreatePostRepo;
import com.fenil.hackathon.Repos.FetchPostsRepo;

import java.util.ArrayList;

public class ApiViewModel extends ViewModel {

    private FetchPostsRepo repository;
    private CreatePostRepo createPostRepo;
    private LiveData<ArrayList<Post>> livepostlist;

    public ApiViewModel(){
        super();
    }

    public void init()
    {
        repository = new FetchPostsRepo();
        livepostlist = repository.getPostsListAsync();
    }

    public void initCreatePost()
    {
        createPostRepo = new CreatePostRepo();
    }

    public void fetchPostsFromApi (){
        repository.fetchContestFromApi();
    }

    public void createPost (){
        createPostRepo.createPostAsyncTask();
    }

    public LiveData<ArrayList<Post>> getAllPosts() {
        return livepostlist;
    }

    public LiveData<CreatePostResponse> getCreatePostResponse(CreatePostRequest createPostRequest) {
        return createPostRepo.getCreatePostResponse(createPostRequest);
    }

}