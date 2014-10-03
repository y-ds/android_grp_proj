package com.yahoo.ds.mathsquares;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.yahoo.ds.mathsquares.model.Problem;

public class MathApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(Problem.class);
		Parse.initialize(this, "GvIBHTjWXHEIq9Y3mLDG4SYlJjJCQ9qVmlelyTvL", "aJNeH1WxNGbbdChnGzJwlysBclLsCOXphENJG9LL");
	}
}
