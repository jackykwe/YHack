package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Student extends Person {
    public boolean optin;
    public Student(String name, Date dob, boolean verified, String personality, String academics, boolean optin) {
        super(name, dob, verified, personality, academics);
        this.optin = optin;
    }

    public Student(int id, String name, Date dob, boolean verified, String personality, String academics, boolean optin) {
        super(id, name, dob, verified, personality, academics);
        this.optin = optin;
    }
    
}
