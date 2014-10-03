package com.yahoo.ds.mathsquares.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Problem implements Parcelable {

	private int size;
	private JSONArray provided;
	private JSONArray operators;
	private JSONArray answers;
	
	public Problem() {
	}

	public int getSize() {
		return size;
	}

	public JSONArray getProvided() {
		return provided;
	}
	
	public ArrayList<Integer> getProvidedList() {
		final ArrayList<Integer> res = new ArrayList<Integer>();
		try { 
			if (provided != null) {
				for (int i=0; i<provided.length(); i++) {
					res.add(provided.getInt(i));
				}
			}
		} catch (JSONException e) {
			Log.d("debug", "Unable to parse provided list for problem", e);
		}
		return res;
	}

	public JSONArray getOperators() {
		return operators;
	}

	public ArrayList<String> getOperatorsList() {
		final ArrayList<String> res = new ArrayList<String>();
		try { 
			if (operators != null) {
				for (int i=0; i<operators.length(); i++) {
					res.add(operators.getString(i));
				}
			}
		} catch (JSONException e) {
			Log.d("debug", "Unable to parse operators list for problem", e);
		}
		return res;
	}
	
	public JSONArray getAnswers() {
		return answers;
	}
	
	public ArrayList<Integer> getAnswersList() {
		final ArrayList<Integer> res = new ArrayList<Integer>();
		try { 
			if (answers != null) {
				for (int i=0; i<answers.length(); i++) {
					res.add(answers.getInt(i));
				}
			}
		} catch (JSONException e) {
			Log.d("debug", "Unable to parse answer list for problem", e);
		}
		return res;
	}

	@Override
	public String toString() {
		return String.format( "Problem [size=%s, provided=%s, operators=%s, answer=%s]", size, provided, operators, answers);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private Problem(Parcel in) {
		size = in.readInt();
		final ArrayList<Integer> providedList = (ArrayList<Integer>)in.readSerializable();
		provided = toJSONArray(providedList);
		final ArrayList<String> operatorsList = (ArrayList<String>)in.readSerializable();
		operators = toJSONArray(operatorsList);
		final ArrayList<Integer> answersList = (ArrayList<Integer>)in.readSerializable();
		answers = toJSONArray(answersList);
	}
	
	private JSONArray toJSONArray(List<?> list) {
		final JSONArray array = new JSONArray();
		for (Object obj : list) {
			array.put(obj);
		}
		return array;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(size);
		out.writeSerializable(getProvidedList());
		out.writeSerializable(getOperatorsList());
		out.writeSerializable(getAnswersList());
	}
	
	public static final Parcelable.Creator<Problem> CREATOR = new Parcelable.Creator<Problem>() {
		@Override
		public Problem createFromParcel(Parcel source) {
			return new Problem(source);
		}
		@Override
		public Problem[] newArray(int size) {
			return new Problem[size];
		}
	};

	public void setSize(int size) {
		this.size = size;
	}

	public void setProvided(JSONArray provided) {
		this.provided = provided;
	}

	public void setOperators(JSONArray operators) {
		this.operators = operators;
	}

	public void setAnswers(JSONArray answers) {
		this.answers = answers;
	}
}
