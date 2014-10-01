package com.yahoo.ds.mathsquares;

import android.app.Application;

import com.parse.Parse;

public class MathApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "GvIBHTjWXHEIq9Y3mLDG4SYlJjJCQ9qVmlelyTvL", "aJNeH1WxNGbbdChnGzJwlysBclLsCOXphENJG9LL");
	}
}
