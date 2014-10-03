package com.yahoo.ds.mathsquares.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yahoo.ds.mathsquares.ParseClient;
import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.adapters.ProblemListAdapter;
import com.yahoo.ds.mathsquares.model.Problem;

public class GameListFragment extends Fragment {

	private List<Problem> problems;
	private ProblemListAdapter problemsAdapter;
	private ListView lvProblems;
	private ParseClient parseClient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parseClient = new ParseClient();
		problems = new ArrayList<Problem>();
		final JSONArray prs = new JSONArray();
		prs.put(2);
		prs.put(4);
		prs.put(3);
		prs.put(1);
		final JSONArray ops = new JSONArray();
		ops.put("+");
		ops.put("+");
		ops.put("+");
		ops.put("+");
		final JSONArray ans = new JSONArray();
		ans.put(1);
		ans.put(1);
		ans.put(1);
		ans.put(1);
		final Problem p = new Problem();
		p.setSize(2);
		p.setProvided(prs);
		p.setOperators(ops);
		p.setAnswers(ans);
		problems.add(p);
		
		final Problem p2 = new Problem();
		p2.setSize(2);
		p2.setProvided(prs);
		p2.setOperators(ops);
		p2.setAnswers(ans);
		problems.add(p2);
		problemsAdapter = new ProblemListAdapter(getActivity(), problems);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_game_list, container, false);
		lvProblems = (ListView)view.findViewById(R.id.lvProblems);
		lvProblems.setAdapter(problemsAdapter);
		return view;
	}
}
