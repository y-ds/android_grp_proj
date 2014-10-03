package com.yahoo.ds.mathsquares.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Problem")
public class Problem extends ParseObject {
	
	public Problem() {
	}

	public int getSize() {
		return getInt("size");
	}

	public JSONArray getProvided() {
		return getJSONArray("provided");
	}
	
	public ArrayList<Integer> getProvidedList() {
		final JSONArray provided = getProvided();
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
		return getJSONArray("operators");
	}

	public ArrayList<String> getOperatorsList() {
		final JSONArray operators = getOperators();
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
		return getJSONArray("answers");
	}
	
	public ArrayList<Integer> getAnswersList() {
		final JSONArray answers = getAnswers();
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
}
