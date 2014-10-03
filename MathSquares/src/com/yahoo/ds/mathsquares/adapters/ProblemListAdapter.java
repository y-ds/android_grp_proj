package com.yahoo.ds.mathsquares.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
		
		setupOperators(problem, v);
		setupProvided(problem, v);
		
		return v;
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
}
