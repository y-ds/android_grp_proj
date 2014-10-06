package com.yahoo.ds.mathsquares.activities;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.yahoo.ds.mathsquares.R;

public class InstructionsActivity extends Activity {

	private ImageView sampleBoard; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);

		try 
		{
		    // get input stream
		    InputStream ims = getAssets().open("math_squares.jpg");
		    // load image as Drawable
		    Drawable d = Drawable.createFromStream(ims, null);
		    // set image to ImageView
		    sampleBoard = (ImageView)findViewById(R.id.ivSampleBoard);
		    sampleBoard.setImageDrawable(d);
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		    
	}
}
