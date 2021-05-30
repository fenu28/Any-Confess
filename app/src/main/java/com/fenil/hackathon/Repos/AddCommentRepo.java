package com.fenil.hackathon.Repos;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fenil.hackathon.Model.AddCommentRequest;
import com.fenil.hackathon.Model.AddCommentResponse;
import com.fenil.hackathon.Model.CommentResponse;
import com.fenil.hackathon.Model.CreatePostRequest;
import com.fenil.hackathon.Model.CreatePostResponse;
import com.fenil.hackathon.Services.APIClient;
import com.fenil.hackathon.Services.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentRepo {

    private ApiInterface apiInterface;
    private FetchApiAsyncTask fetchApiAsyncTask;

    public AddCommentRepo(){
        apiInterface = APIClient.getClient().create(ApiInterface.class);
    }

    public LiveData<AddCommentResponse> getAddCommentResponse(String postid,AddCommentRequest addCommentRequest){
        Call<AddCommentResponse> call = apiInterface.addComment(postid,addCommentRequest);
        fetchApiAsyncTask = new FetchApiAsyncTask(call);
        return fetchApiAsyncTask.getResponse();
    }

    public void addCommentAsyncTask(){
        fetchApiAsyncTask.execute();
    }

    private static class FetchApiAsyncTask extends AsyncTask<Void,Void,Void> {

        private Call<AddCommentResponse> call;
        private MutableLiveData<AddCommentResponse> createPostResponse;

        private FetchApiAsyncTask(Call<AddCommentResponse> call){
            this.call = call;
            createPostResponse = new MutableLiveData<>();
        }

        private MutableLiveData<AddCommentResponse> getResponse() {
            return createPostResponse;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            call.enqueue(new Callback<AddCommentResponse>() {
                @Override
                public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                    Log.e("APIFETCHEDREPOASYNC>>",response.code()+" ");
                    if(response.code()==201)
                    {
                        AddCommentResponse apiResponse = response.body();
                        assert apiResponse != null;
                        createPostResponse.postValue(apiResponse);
                    }
                    else
                    {
                        createPostResponse.postValue(new AddCommentResponse("Your Post is Toxic Content, so can't be posted !",new CommentResponse()));
                    }
                }

                @Override
                public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                    Log.e("API FETCH ERROR>>>", Objects.requireNonNull(t.getMessage()));
                }
            });
            return null;
        }
    }

}
