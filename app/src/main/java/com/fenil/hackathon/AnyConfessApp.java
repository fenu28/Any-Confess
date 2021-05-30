package com.fenil.hackathon;

import android.app.Application;
import android.content.Context;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnyConfessApp extends Application {

    Index index;
    Client client;

    public static AnyConfessApp get(Context context) {
        return (AnyConfessApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        client = new Client("8T5247D7XL", "75ceb6394a423c609f3d49fa74fcba0f");
        index = client.getIndex("hashtags");

    }

    public Index getIndex(){
        return index;
    }
}
