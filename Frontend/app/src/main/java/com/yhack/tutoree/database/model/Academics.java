package com.yhack.tutoree.database.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Academics {
    JSONObject everything;
    ArrayList<String> activities;
    ArrayList<String> interests;
    Map<String, ArrayList<Integer>> grades;

    public Academics(ArrayList<String> activities, ArrayList<String> interests, Map<String, ArrayList<Integer>> grades) {
        this.activities = activities;
        this.interests = interests;
        this.grades = grades;
    }

    public Academics() {

    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public Map<String, ArrayList<Integer>> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, ArrayList<Integer>> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        everything = new JSONObject();
        JSONObject resultsJSON = new JSONObject(grades);
        try {
            everything.put("activities", new JSONArray(activities));
            everything.put("interests", new JSONArray(interests));
            everything.put("results", resultsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return everything.toString();
    }

    public static Academics stringParser(String jsonString) throws JSONException {
        JSONObject acads = new JSONObject(jsonString);
        Academics acadObj = new Academics();

        JSONArray activityJSONArr = (JSONArray) acads.get("activities");
        ArrayList<String> activities = new ArrayList<>();
        for(int i = 0; i < activityJSONArr.length(); i++) {
            activities.add(activityJSONArr.getString(i));
        }

        JSONArray interestJSONArr = (JSONArray) acads.get("interests");
        ArrayList<String> interests = new ArrayList<>();
        for(int i = 0; i < activityJSONArr.length(); i++) {
            interests.add(interestJSONArr.getString(i));
        }

        JSONObject resultJSONObj = (JSONObject) acads.get("results");
        Map<String, ArrayList<Integer>> grades = new HashMap<>();
        Iterator<String> keys = resultJSONObj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            JSONArray val = resultJSONObj.getJSONArray(key);
            ArrayList<Integer> res = new ArrayList<>();
            for(int i = 0; i < val.length(); i++) {
                res.add(val.getInt(i));
            }
            grades.put(key, res);
        }

        acadObj.setActivities(activities);
        acadObj.setInterests(interests);
        acadObj.setGrades(grades);
        return acadObj;
    }
}
