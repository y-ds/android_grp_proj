package com.yahoo.ds.mathsquares.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.adapters.ProblemListAdapter;
import com.yahoo.ds.mathsquares.model.Problem;

public class GameListFragment extends Fragment {

	private List<Problem> problems;
	private ProblemListAdapter problemsAdapter;
	private ListView lvProblems;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		problems = new ArrayList<Problem>();
		problemsAdapter = new ProblemListAdapter(getActivity(), problems);
		getProblems();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_game_list, container, false);
		lvProblems = (ListView)view.findViewById(R.id.lvProblems);
		lvProblems.setAdapter(problemsAdapter);
		return view;
	}
	
	public void getProblems() {
		final ParseQuery<Problem> query = ParseQuery.getQuery("Problem");
		query.orderByDescending("problem_date");
		query.findInBackground(new FindCallback<Problem>() {
		    public void done(List<Problem> newProblems, ParseException e) {
		        if (e == null) {
		        	problemsAdapter.addAll(newProblems);
		        } else {
		            Log.d("problem", "Error: " + e.getMessage());
		        }
		    }
		});
	}
}
