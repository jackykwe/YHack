package com.yhack.tutoree.database.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
            everything.put("activities", activities);
            everything.put("interests", interests);
            everything.put("results", resultsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return everything.toString();
    }

    public static Academics stringParser(String jsonString) throws JSONException {
        JSONObject acads = new JSONObject(jsonString);
        Academics acadObj = new Academics();
        System.out.println(acads.get("activities"));

//        acads.getJSONArray("activities");
//        JSONArray acts = new JSONArray(acads.getJSONArray("activities"));
//        ArrayList<String> l1 = new ArrayList<>();
//        for(int i = 0; i < acts.length(); i++) {
//            l1.add(acts.get(i).toString());
//        }
//        JSONArray ints = new JSONArray(acads.get("interests"));
//        ArrayList<String> l2 = new ArrayList<>();
//        for(int i = 0; i < ints.length(); i++) {
//            l2.add(ints.get(i).toString());
//        }
//
//        JSONArray ress = new JSONArray(acads.get("results"));
//
//
//        acadObj.setActivities(l1);
//        acadObj.setInterests(l2);
//        acadObj.setGrades((Map<String, ArrayList<Integer>>) acads.get("results"));
        return acadObj;
    }
}
