package com.yahoo.ds.mathsquares.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.adapters.PlayerScoreArrayAdapter;
import com.yahoo.ds.mathsquares.model.PlayerResult;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LeaderboardActivity extends Activity {

	
	ArrayList<PlayerResult> playerResults = new ArrayList<PlayerResult>();
	PlayerScoreArrayAdapter playerScoreAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
        
		playerScoreAdapter = new PlayerScoreArrayAdapter(this, playerResults);
//        .setAdapter(playerScoreAdapter);
		
		getPlayerInfo(); 

    }
    
    public void getPlayerInfo()
    {
    	//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android%27
    	AsyncHttpClient client = new AsyncHttpClient();
    	String url = "";
    	client.get(url, 
    			new JsonHttpResponseHandler(){
    				@Override
    				public void onSuccess(JSONObject response){
    					JSONArray imageJsonResults = null;
    					try{
    						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    						playerResults.addAll(PlayerResult.fromJSONArray(imageJsonResults));
    					}
    					catch(JSONException e)
    					{
    						e.printStackTrace();
    					}
    				}
    		});
    }
	
}

