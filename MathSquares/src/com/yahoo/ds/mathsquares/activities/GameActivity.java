package com.yahoo.ds.mathsquares.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.fragments.GameListFragment;

public class GameActivity extends FragmentActivity {
	
    private static final String TAG = GameActivity.class.getName();
    private static String sUserId;
    private static final Integer INSTRUCTIONS_INTENT = 5;
    private static final Integer LEADERBOARD_INTENT = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		if (ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();
        } else {
            login();
        }
	}
	
	
	public void onSettingsLeaderboard(MenuItem mi)
	{
    	Intent i = new Intent(this, LeaderboardActivity.class);
    	//Pass arguments
    	//Execute Intent startActivityForResults
    	startActivityForResult(i, LEADERBOARD_INTENT);
	}
	
	public void onSettingsHelp(MenuItem mi)
	{
    	Intent i = new Intent(this, InstructionsActivity.class);
    	//Pass arguments
    	//Execute Intent startActivityForResults
    	startActivityForResult(i, INSTRUCTIONS_INTENT); 
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
	
	private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();		
    }
    
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
	    @Override
	    public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed.");
                } else {
                	Log.d(TAG, "Logged in with user " + sUserId);
                    startWithCurrentUser();
                }
            }
       });		
    }
    
    public void onFilter(MenuItem item) {
    	final GameListFragment glf = (GameListFragment)getSupportFragmentManager().findFragmentById(R.id.gameListFragment);
    	glf.filterProblems();
    }
    
    public void onInstructions(MenuItem item) {
    	final Intent intent = new Intent(this, InstructionsActivity.class);
    	startActivity(intent);
    }
    
    public void onLeaderboard(MenuItem item) {
    	final Intent intent = new Intent(this, LeaderboardActivity.class);
    	startActivity(intent);
    }
}
