package com.yahoo.ds.mathsquares.adapters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;
import com.yahoo.ds.mathsquares.R;
import com.yahoo.ds.mathsquares.model.Problem;

public class ProblemListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<Problem> problems;
	
	public ProblemListAdapter(Context context, List<Problem> problems) {
		this.context = context;
		this.problems = problems;
    }

	public void addAll(List<Problem> newProblems) {
		if (problems == null) {
			problems = new ArrayList<Problem>();
		}
		problems.addAll(newProblems);
	}
	
	private boolean correctAnswer(Problem problem, View v) {
		try {
			final List<Integer> answers = problem.getAnswersList();
			final List<Integer> submittedAnswers = getSubmittedAnswers(v);
			
			boolean correct = true;
			for (int i=0; i<answers.size(); i++) {
				if (!answers.get(i).equals(submittedAnswers.get(i))) {
					correct = false;
				}
			}
			return correct;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private List<EditText> getAnswerEditTexts(View v) {
		final List<EditText> ets = new ArrayList<EditText>();
		ets.add((EditText)v.findViewById(R.id.answer1));
		ets.add((EditText)v.findViewById(R.id.answer2));
		ets.add((EditText)v.findViewById(R.id.answer3));
		ets.add((EditText)v.findViewById(R.id.answer4));
		return ets;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return problems.get(groupPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 1000000 + groupPosition;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Problem problem = (Problem)getGroup(groupPosition);
		final View v;
		if (convertView == null) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			v = inflater.inflate(R.layout.problem_item, parent, false);
		} else {
			v = convertView;
		}
		final boolean completed = problemCompleted(problem);
		setupOperators(problem, v);
		setupProvided(problem, v);
		setupAnswers(problem, v, completed);
		setupSolveButton(problem, v);
		setupHintButton(problem, v);
		return v;
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		return problems.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		return problems.size();
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final Problem problem = (Problem)getGroup(groupPosition);
		final View v;
		if (convertView == null) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			v = inflater.inflate(R.layout.problem_header, parent, false);
		} else {
			v = convertView;
		}
		setupHeader(problem, v);
		return v;
	}
	
	private List<TextView> getOperatorsTextViews(View v) {
		final List<TextView> ets = new ArrayList<TextView>();
		ets.add((TextView)v.findViewById(R.id.operator1));
		ets.add((TextView)v.findViewById(R.id.operator2));
		ets.add((TextView)v.findViewById(R.id.operator3));
		ets.add((TextView)v.findViewById(R.id.operator4));
		return ets;
	}

	private List<TextView> getProvidedTextViews(View v) {
		final List<TextView> ets = new ArrayList<TextView>();
		ets.add((TextView)v.findViewById(R.id.provided1));
		ets.add((TextView)v.findViewById(R.id.provided2));
		ets.add((TextView)v.findViewById(R.id.provided3));
		ets.add((TextView)v.findViewById(R.id.provided4));
		return ets;
	}

	private List<Integer> getSubmittedAnswers(View v) {
		final List<Integer> res = new ArrayList<Integer>();
		final List<EditText> ets = getAnswerEditTexts(v);
		for (EditText et : ets) {
			final String str = et.getText().toString().trim();
			final Integer i = str.isEmpty() ? null : Integer.parseInt(str);
			res.add(i);
		}
		return res;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private boolean problemCompleted(Problem problem) {
		final ParseUser user = ParseUser.getCurrentUser();
		final JSONArray completedProblems = user.getJSONArray("completed_problems");
		if (completedProblems != null) {
			for (int i=0; i<completedProblems.length(); i++) {
				try {
					String id = null;
					if (completedProblems.get(i) instanceof JSONObject) {
						final JSONObject jObj = (JSONObject)completedProblems.get(i);
						final Object idObj = jObj.get("objectId");
						id = idObj == null ? null : idObj.toString();
					} else if (completedProblems.get(i) instanceof Problem) {
						final Problem pr = (Problem)completedProblems.get(i);
						id = pr.getObjectId();
					}
					if (problem.getObjectId().equals(id)) {
						return true;
					}
				} catch (JSONException e) {
				}
			}
		}
		return false;
	}

	private void setupAnswers(Problem problem, View v, boolean completed) {
		final List<Integer> answers = problem.getAnswersList();
		final List<EditText> ets = getAnswerEditTexts(v);
		for (int i=0; i<ets.size(); i++) {
			final EditText et = ets.get(i);
			if (completed) {
				et.setText(answers.get(i).toString());
				et.setEnabled(false);
			} else {
				et.setText("");
				et.setEnabled(true);
			}
		}
	}

	private void setupHeader(final Problem problem, final View v) {
		final TextView tvProblemTitle = (TextView)v.findViewById(R.id.tvProblemTitle);
		tvProblemTitle.setText(problem.getTitle());
		
		final CheckBox cbProblemCreated = (CheckBox)v.findViewById(R.id.cbProblemCompleted);
		cbProblemCreated.setChecked(problemCompleted(problem));
	}

	private void setupHintButton(final Problem problem, final View v) {
		final Button hintButton = (Button)v.findViewById(R.id.hintButton);
		if (problemCompleted(problem)) {
			hintButton.setVisibility(View.INVISIBLE);
			hintButton.setEnabled(false);
		} else {
			hintButton.setVisibility(View.VISIBLE);
			hintButton.setEnabled(true);
			hintButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v2) {
					final List<EditText> ets = getAnswerEditTexts(v);
					final List<Integer> answers = problem.getAnswersList();
					final List<Integer> submittedAnswers = getSubmittedAnswers(v);

					final int bgColorRed = Color.parseColor("#f42500");
					final int bgColorGreen = Color.parseColor("#50d07f");
					for (int i=0; i<ets.size(); i++) {
						ets.get(i).setBackgroundColor(answers.get(i).equals(submittedAnswers.get(i)) ? bgColorGreen : bgColorRed);
					}
				}
			});
		}
	}

	private void setupOperators(Problem problem, View v) {
		final List<String> operators = problem.getOperatorsList();
		final List<TextView> tvs = getOperatorsTextViews(v);
		for (int i=0; i<tvs.size(); i++) {
			tvs.get(i).setText(operators.get(i));
		}
	}

	private void setupProvided(Problem problem, View v) {
		final List<Integer> provided = problem.getProvidedList();
		final List<TextView> tvs = getProvidedTextViews(v);
		for (int i=0; i<tvs.size(); i++) {
			tvs.get(i).setText(provided.get(i).toString());
		}
	}
	
	private void setupSolveButton(final Problem problem, final View v) {
		final ParseUser user = ParseUser.getCurrentUser();
		final JSONArray completedProblems = user.getJSONArray("completed_problems");
		final Button solveButton = (Button)v.findViewById(R.id.solveButton);
		if (problemCompleted(problem)) {
			solveButton.setText("Solved");
			solveButton.setEnabled(false);
		} else {
			solveButton.setText("Solve");
			solveButton.setEnabled(true);
			solveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v2) {
					if (correctAnswer(problem, v)) {
						if (completedProblems == null) {
							user.put("completed_problems", new JSONArray());
						}
						final JSONArray cps = user.getJSONArray("completed_problems");
						cps.put(problem);
						user.put("score", completedProblems.length());
						user.saveInBackground();
						notifyDataSetChanged();
					}
				}
			});
		}
	}
}
