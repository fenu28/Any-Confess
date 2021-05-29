package com.fenil.hackathon.Repos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fenil.hackathon.MainActivity;
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

public class FetchPostsRepo {

    private ApiInterface apiInterface;
    private FetchApiAsyncTask fetchApiAsyncTask;

    public FetchPostsRepo(){

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        Call<ArrayList<PostsApiResponse>> call = apiInterface.getAllContestsFromApi(new PostsFetchRequest(MainActivity.timestamp));
        fetchApiAsyncTask = new FetchApiAsyncTask(call);

    }

    public LiveData<ArrayList<Post>> getPostsListAsync(){
        return fetchApiAsyncTask.getLivePostsList();
    }

    public void fetchContestFromApi(){
        fetchApiAsyncTask.execute();
    }

    private static class FetchApiAsyncTask extends AsyncTask<Void,Void,Void> {

        private Call<ArrayList<PostsApiResponse>> call;
        private MutableLiveData<ArrayList<Post>> liveContestList;

        private FetchApiAsyncTask(Call<ArrayList<PostsApiResponse>> call){
            this.call = call;
            liveContestList = new MutableLiveData<>();
        }

        private MutableLiveData<ArrayList<Post>> getLivePostsList() {
            return liveContestList;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            call.enqueue(new Callback<ArrayList<PostsApiResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<PostsApiResponse>> call, Response<ArrayList<PostsApiResponse>> response) {

                    Log.e("APIFETCHEDREPOASYNC>>",response.code()+" ");
                    List<PostsApiResponse> apiResponse = response.body();
                    assert apiResponse != null;

                    ArrayList<Post> postList = new ArrayList<>();
                    Log.v("ApiResponseSize",String.valueOf(apiResponse.size()));
                    for(int i=0 ;i < apiResponse.size();i++)
                    {
                        postList.add(new Post(apiResponse.get(i).getText(),apiResponse.get(i).getId(),apiResponse.get(i).getTimestamp(),apiResponse.get(i).getHashtags()));
                    }
                    liveContestList.postValue(postList);
                }

                @Override
                public void onFailure(Call<ArrayList<PostsApiResponse>> call, Throwable t) {
                    Log.e("API FETCH ERROR>>>", Objects.requireNonNull(t.getMessage()));
                }
            });
            return null;
        }

    }
}

