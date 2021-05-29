package com.fenil.hackathon.Repos;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fenil.hackathon.Model.CreatePostRequest;
import com.fenil.hackathon.Model.CreatePostResponse;
import com.fenil.hackathon.Model.Post;
import com.fenil.hackathon.Model.PostsApiResponse;
import com.fenil.hackathon.Model.PostsFetchRequest;
import com.fenil.hackathon.Services.APIClient;
import com.fenil.hackathon.Services.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostRepo {

    private ApiInterface apiInterface;
    private FetchApiAsyncTask fetchApiAsyncTask;

    public CreatePostRepo(){
        apiInterface = APIClient.getClient().create(ApiInterface.class);
    }

    public LiveData<CreatePostResponse> getCreatePostResponse(CreatePostRequest createPostRequest){
        Call<CreatePostResponse> call = apiInterface.addPost(createPostRequest);
        fetchApiAsyncTask = new FetchApiAsyncTask(call);
        return fetchApiAsyncTask.getResponse();
    }

    public void createPostAsyncTask(){
        fetchApiAsyncTask.execute();
    }

    private static class FetchApiAsyncTask extends AsyncTask<Void,Void,Void> {

        private Call<CreatePostResponse> call;
        private MutableLiveData<CreatePostResponse> createPostResponse;

        private FetchApiAsyncTask(Call<CreatePostResponse> call){
            this.call = call;
            createPostResponse = new MutableLiveData<>();
        }

        private MutableLiveData<CreatePostResponse> getResponse() {
            return createPostResponse;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            call.enqueue(new Callback<CreatePostResponse>() {
                @Override
                public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {

                    Log.e("APIFETCHEDREPOASYNC>>",response.code()+" ");
                    CreatePostResponse apiResponse = response.body();
                    assert apiResponse != null;
                    createPostResponse.postValue(apiResponse);
                }

                @Override
                public void onFailure(Call<CreatePostResponse> call, Throwable t) {
                    Log.e("API FETCH ERROR>>>", Objects.requireNonNull(t.getMessage()));
                }
            });
            return null;
        }
    }
}

