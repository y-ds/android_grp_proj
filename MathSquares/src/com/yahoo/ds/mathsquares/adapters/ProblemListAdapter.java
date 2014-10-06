package com.yahoo.ds.mathsquares.adapters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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

	private void setupOperators(Problem problem, View v) {
		final List<String> operators = problem.getOperatorsList();
		
		final TextView tvOp1 = (TextView)v.findViewById(R.id.operator1);
		final TextView tvOp2 = (TextView)v.findViewById(R.id.operator2);
		final TextView tvOp3 = (TextView)v.findViewById(R.id.operator3);
		final TextView tvOp4 = (TextView)v.findViewById(R.id.operator4);
		
		tvOp1.setText(operators.get(0));
		tvOp2.setText(operators.get(1));
		tvOp3.setText(operators.get(2));
		tvOp4.setText(operators.get(3));
	}
	
	private void setupProvided(Problem problem, View v) {
		final List<Integer> provided = problem.getProvidedList();
		
		final TextView tvPro1 = (TextView)v.findViewById(R.id.provided1);
		final TextView tvPro2 = (TextView)v.findViewById(R.id.provided2);
		final TextView tvPro3 = (TextView)v.findViewById(R.id.provided3);
		final TextView tvPro4 = (TextView)v.findViewById(R.id.provided4);
		
		tvPro1.setText(provided.get(0).toString());
		tvPro2.setText(provided.get(1).toString());
		tvPro3.setText(provided.get(2).toString());
		tvPro4.setText(provided.get(3).toString());
	}
	
	private void setupSolveButton(final Problem problem, final View v) {
		final ParseUser user = ParseUser.getCurrentUser();
		final JSONArray completedProblems = user.getJSONArray("completed_problems");
		final Button solveButton = (Button)v.findViewById(R.id.solveButton);
		solveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v2) {
				if (correctAnswer(problem, v)) {
					if (completedProblems == null) {
						user.put("completed_problems", new JSONArray());
					}
					final JSONArray cps = user.getJSONArray("completed_problems");
					cps.put(problem);
					user.saveInBackground();
				}
			}
		});
	}
	
	private void setupHeader(final Problem problem, final View v) {
		final TextView tvProblemTitle = (TextView)v.findViewById(R.id.tvProblemTitle);
		tvProblemTitle.setText(problem.getTitle());
		
		final ParseUser user = ParseUser.getCurrentUser();
		final JSONArray completedProblems = user.getJSONArray("completed_problems");
		
		boolean checked = false;
		if (completedProblems != null) {
			for (int i=0; i<completedProblems.length(); i++) {
				try {
					final JSONObject jObj = (JSONObject)completedProblems.get(i);
					final Object idObj = jObj.get("objectId");
					final String id = idObj == null ? null : idObj.toString();
					if (problem.getObjectId().equals(id)) {
						checked = true;
					}
				} catch (JSONException e) {
				}
			}
		}
		
		final CheckBox cbProblemCreated = (CheckBox)v.findViewById(R.id.cbProblemCompleted);
		cbProblemCreated.setChecked(checked);
	}
	
	private boolean correctAnswer(Problem problem, View v) {
		final List<Integer> answers = problem.getAnswersList();
		
		final EditText answer1 = (EditText)v.findViewById(R.id.answer1);
		final EditText answer2 = (EditText)v.findViewById(R.id.answer2);
		final EditText answer3 = (EditText)v.findViewById(R.id.answer3);
		final EditText answer4 = (EditText)v.findViewById(R.id.answer4);
		
		try {
			final Integer a1 = Integer.parseInt(answer1.getText().toString());
			final Integer a2 = Integer.parseInt(answer2.getText().toString());
			final Integer a3 = Integer.parseInt(answer3.getText().toString());
			final Integer a4 = Integer.parseInt(answer4.getText().toString());
		
			final Integer b1 = answers.get(0);
			final Integer b2 = answers.get(1);
			final Integer b3 = answers.get(2);
			final Integer b4 = answers.get(3);
			
			return a1.equals(b1) && a2.equals(b2) && a3.equals(b3) && a4.equals(b4);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public int getGroupCount() {
		return problems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return problems.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return problems.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 1000000 + groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
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
		setupOperators(problem, v);
		setupProvided(problem, v);
		setupSolveButton(problem, v);
		return v;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public void addAll(List<Problem> newProblems) {
		if (problems == null) {
			problems = new ArrayList<Problem>();
		}
		problems.addAll(newProblems);
	}
}
