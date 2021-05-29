package com.fenil.hackathon.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fenil.hackathon.Model.Post;
import com.fenil.hackathon.Repos.FetchPostsRepo;

import java.util.ArrayList;

public class ApiViewModel extends ViewModel {

    private FetchPostsRepo repository;
    private LiveData<ArrayList<Post>> livepostlist;

    public ApiViewModel(){
        super();
    }

    public void init()
    {
        repository = new FetchPostsRepo();
        livepostlist = repository.getPostsListAsync();
    }

    public void fetchPostsFromApi (){
        repository.fetchContestFromApi();
    }

    public LiveData<ArrayList<Post>> getAllPosts() {
        return livepostlist;
    }

}