package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Person {
    public int id;
    public String name;
    public Date dob;
    public boolean verified;
    public String personality;
    public String academics;

    public Person(String name, Date dob, boolean verified, String personality, String academics) {
        this.name = name;
        this.dob = dob;
        this.verified = verified;
        this.personality = personality;
        this.academics = academics;
    }

    public Person(int id, String name, Date dob, boolean verified, String personality, String academics) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.verified = verified;
        this.personality = personality;
        this.academics = academics;
    }
    
}
