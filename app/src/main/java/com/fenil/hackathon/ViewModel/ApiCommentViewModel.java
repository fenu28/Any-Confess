package com.fenil.hackathon.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fenil.hackathon.Model.AddCommentRequest;
import com.fenil.hackathon.Model.AddCommentResponse;
import com.fenil.hackathon.Model.GetCommentsResponse;
import com.fenil.hackathon.Repos.AddCommentRepo;
import com.fenil.hackathon.Repos.GetCommentsRepo;

public class ApiCommentViewModel extends ViewModel {

    private AddCommentRepo addCommentRepo;
    private GetCommentsRepo getCommentsRepo;

    public ApiCommentViewModel(){
        super();
    }

    public void initGetComment()
    {
        getCommentsRepo = new GetCommentsRepo();
    }

    public void initAddComment()
    {
        addCommentRepo = new AddCommentRepo();
    }

    public void getComments (){
        getCommentsRepo.getCommentsAsyncTask();
    }

    public void addComment (){
        addCommentRepo.addCommentAsyncTask();
    }

    public LiveData<GetCommentsResponse> getCommentResponse(String postid) {
        return getCommentsRepo.getCommentResponse(postid);
    }

    public LiveData<AddCommentResponse> getAddCommentResponse(String postid,AddCommentRequest addCommentRequest) {
        return addCommentRepo.getAddCommentResponse(postid,addCommentRequest);
    }
}
