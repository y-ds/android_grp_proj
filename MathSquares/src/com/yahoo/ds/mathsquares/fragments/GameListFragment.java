package com.yahoo.ds.mathsquares.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.adapters.ProblemListAdapter;
import com.yahoo.ds.mathsquares.model.Problem;

public class GameListFragment extends Fragment {

	private List<Problem> problems;
	private List<Problem> allProblems;
	private ProblemListAdapter problemsAdapter;
	private ExpandableListView lvProblems;
	private boolean filterEnabled;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filterEnabled = false;
		problems = new ArrayList<Problem>();
		allProblems = new ArrayList<Problem>();
		problemsAdapter = new ProblemListAdapter(getActivity(), problems);
		getProblems();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_game_list, container, false);
		lvProblems = (ExpandableListView)view.findViewById(R.id.lvProblems);
		lvProblems.setAdapter(problemsAdapter);
		return view;
	}
	
	public void getProblems() {
		final ParseQuery<Problem> query = ParseQuery.getQuery("Problem");
		query.whereLessThanOrEqualTo("problem_date", new Date());
		query.orderByDescending("problem_date");
		query.findInBackground(new FindCallback<Problem>() {
		    public void done(List<Problem> newProblems, ParseException e) {
		        if (e == null) {
		        	allProblems.addAll(newProblems);
		        	problemsAdapter.addAll(newProblems);
		    		lvProblems.expandGroup(0);
		        	problemsAdapter.notifyDataSetChanged();
		        } else {
		            Log.d("problem", "Error: " + e.getMessage());
		        }
		    }
		});
	}
	
	public void filterProblems() {
		filterEnabled = !filterEnabled;
		if (filterEnabled) {
			final ParseUser user = ParseUser.getCurrentUser();
			final JSONArray array = user.getJSONArray("completed_problems");
			final Set<String> completedProblems = new HashSet<String>();
			if (array != null) {
				for (int i=0; i<array.length(); i++) {
					try {
						final Object obj = array.get(i);
						final String str;
						if (obj instanceof Problem) {
							final Problem pr = (Problem)obj;
							str = pr.getObjectId();
						} else if (obj instanceof JSONObject) {
							final JSONObject jo = (JSONObject)obj;
							str = jo.getString("objectId");
						} else {
							str = null;
						}
						if (str != null) {
							completedProblems.add(str);
						}
					} catch (JSONException e) {
					}
				}
			}
			problems.clear();
			for (Problem p : allProblems) {
				if (!completedProblems.contains(p.getObjectId())) {
					problems.add(p);
				}
			}
		} else {
			problems.clear();
			problems.addAll(allProblems);
		}
		problemsAdapter.notifyDataSetChanged();
	}
}
