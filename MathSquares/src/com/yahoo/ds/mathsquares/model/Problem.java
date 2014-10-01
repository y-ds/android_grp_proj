package com.yahoo.ds.mathsquares.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.parse.ParseObject;

public class Problem extends ParseObject {

	private int size;
	private JSONArray provided;
	private JSONArray operators;
	private JSONArray answer;
	
	public Problem() {
	}

	public int getSize() {
		return size;
	}

	public JSONArray getProvided() {
		return provided;
	}
	
	public List<Integer> getProvidedList() {
		final List<Integer> res = new ArrayList<Integer>();
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

	public List<String> getOperatorList() {
		final List<String> res = new ArrayList<String>();
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
	
	public JSONArray getAnswer() {
		return answer;
	}
	
	public List<Integer> getAnswerList() {
		final List<Integer> res = new ArrayList<Integer>();
		try { 
			if (answer != null) {
				for (int i=0; i<answer.length(); i++) {
					res.add(answer.getInt(i));
				}
			}
		} catch (JSONException e) {
			Log.d("debug", "Unable to parse answer list for problem", e);
		}
		return res;
	}

	@Override
	public String toString() {
		return String.format( "Problem [size=%s, provided=%s, operators=%s, answer=%s]", size, provided, operators, answer);
	}
}
