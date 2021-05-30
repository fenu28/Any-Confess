package com.fenil.hackathon.Repos;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fenil.hackathon.Model.AddCommentRequest;
import com.fenil.hackathon.Model.AddCommentResponse;
import com.fenil.hackathon.Model.GetCommentsResponse;
import com.fenil.hackathon.Services.APIClient;
import com.fenil.hackathon.Services.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCommentsRepo {

    private ApiInterface apiInterface;
    private FetchApiAsyncTask fetchApiAsyncTask;

    public GetCommentsRepo(){
        apiInterface = APIClient.getClient().create(ApiInterface.class);
    }

    public LiveData<GetCommentsResponse> getCommentResponse(String postid){
        Call<GetCommentsResponse> call = apiInterface.getComment(postid);
        fetchApiAsyncTask = new FetchApiAsyncTask(call);
        return fetchApiAsyncTask.getResponse();
    }

    public void getCommentsAsyncTask(){
        fetchApiAsyncTask.execute();
    }

    private static class FetchApiAsyncTask extends AsyncTask<Void,Void,Void> {

        private Call<GetCommentsResponse> call;
        private MutableLiveData<GetCommentsResponse> getCommentsResponse;

        private FetchApiAsyncTask(Call<GetCommentsResponse> call){
            this.call = call;
            getCommentsResponse = new MutableLiveData<>();
        }

        private MutableLiveData<GetCommentsResponse> getResponse() {
            return getCommentsResponse;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            call.enqueue(new Callback<GetCommentsResponse>() {
                @Override
                public void onResponse(Call<GetCommentsResponse> call, Response<GetCommentsResponse> response) {
                    Log.e("APIFETCHEDREPOASYNC>>",response.code()+" ");
                    GetCommentsResponse apiResponse = response.body();
                    assert apiResponse != null;
                    getCommentsResponse.postValue(apiResponse);
                }
                @Override
                public void onFailure(Call<GetCommentsResponse> call, Throwable t) {
                    Log.e("API FETCH ERROR>>>", Objects.requireNonNull(t.getMessage()));
                }
            });
            return null;
        }
    }

}
