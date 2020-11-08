package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Person {
    int id;
    String name;
    String gender;
    Date dob;
    boolean verified;
    String school;
    int personality;
    String academics;
    Map<String, Double> ability = new HashMap<>();

    public Person(int id, String name, String gender, Date dob, boolean verified, String school, int personality,
            String academics) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.verified = verified;
        this.school = school;
        this.personality = personality;
        this.academics = academics;
    }

    public Person(String name, String gender, Date dob, boolean verified, String school, int personality,
            String academics) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.verified = verified;
        this.school = school;
        this.personality = personality;
        this.academics = academics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getPersonality() {
        return personality;
    }

    public void setPersonality(int personality) {
        this.personality = personality;
    }

    public String getAcademics() {
        return academics;
    }

    public void setAcademics(String academics) {
        this.academics = academics;
    }

    public Map<String, Double> getAbility() {
        return ability;
    }

    public void setAbility(Map<String, Double> ability) {
        this.ability = ability;
    }


    
}
