package com.yahoo.ds.mathsquares.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.model.Problem;

public class ProblemListAdapter extends ArrayAdapter<Problem> {

	public ProblemListAdapter(Context context, List<Problem> problems) {
		super(context, 0, problems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Problem problem = getItem(position);
		final View v;
		if (convertView == null) {
			final LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.problem_item, parent, false);
		} else {
			v = convertView;
		}
		return v;
	}
}
