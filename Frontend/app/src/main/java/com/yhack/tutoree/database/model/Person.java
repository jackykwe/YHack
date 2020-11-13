package com.yhack.tutoree.database.model;
// package uk.ac.cam.jk810.yhack20.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores details about a person
 */
// implements Serializable to allow for use of this class in SafeArgs
public class Person implements Serializable {
    /**
     * userid
     */
    String username;
    /**
     * hashed password
     */
    String password;
    /**
     * full name
     */
    String name;
    /**
     * gender of the person
     */
    String gender;
    /**
     * date of birth
     */
    Date dob;
    /**
     * whether the person has verified their identity
     */
    boolean verified;
    /**
     * school last studied at
     */
    String school;
    /**
     * 4-bits storing MBTI result
     */
    int personality;
    /**
     * JSON string storing academic results
     */
    String academics;
    /**
     * {@link Map} of subject to ML-generated ability score
     */
    Map<String, Double> ability = new HashMap<>();
    /**
     * Subjects that the person is offering to teach/learn
     */
    List<String> offering = new ArrayList<String>();



    public Person(String username, String name, String gender, Date dob, boolean verified, String school, int personality,
                  String academics) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.verified = verified;
        this.school = school;
        this.personality = personality;
        this.academics = academics;
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * @return String
     */
    public String getName() {
        return name;
    }


    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return String
     */
    public String getGender() {
        return gender;
    }


    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * @return Date
     */
    public Date getDob() {
        return dob;
    }


    /**
     * @param dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }


    /**
     * @return boolean
     */
    public boolean isVerified() {
        return verified;
    }


    /**
     * @param verified
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }


    /**
     * @return String
     */
    public String getSchool() {
        return school;
    }


    /**
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }


    /**
     * @return int
     */
    public int getPersonality() {
        return personality;
    }


    /**
     * @param personality
     */
    public void setPersonality(int personality) {
        this.personality = personality;
    }


    /**
     * @return String
     */
    public String getAcademics() {
        return academics;
    }

    /**
     * @return Academics Object
     */
    public Academics getAcademicsObj() {
        Academics acads = new Academics();
        try {
            acads = Academics.stringParser(academics);
        }catch( Exception e ){
            e.printStackTrace();
        }
        return acads;
    }

    /**
     * @param academics
     */
    public void setAcademics(String academics) {
        this.academics = academics;
    }


    /**
     * @return Map<String, Double>
     */
    public Map<String, Double> getAbility() {
        return ability;
    }


    /**
     * @param ability
     */
    public void setAbility(Map<String, Double> ability) {
        this.ability = ability;
    }


    /**
     * @return String
     */
    public String getUsername() {
        return username;
    }


    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * @return String
     */
    public String getPassword() {
        return password;
    }


    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getOffering() {
        return offering;
    }

    public void setOffering(List<String> offering) {
        this.offering = offering;
    }
}
