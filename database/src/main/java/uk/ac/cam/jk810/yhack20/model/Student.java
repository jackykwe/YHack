package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Student extends Person {
    boolean optin;

    public Student(int id, String name, String gender, Date dob, boolean verified, String school, int personality,
            String academics, boolean optin) {
        super(id, name, gender, dob, verified, school, personality, academics);
        this.optin = optin;
    }

    public Student(String name, String gender, Date dob, boolean verified, String school, int personality,
            String academics, boolean optin) {
        super(name, gender, dob, verified, school, personality, academics);
        this.optin = optin;
    }

    public boolean isOptin() {
        return optin;
    }

    public void setOptin(boolean optin) {
        this.optin = optin;
    }
    
}
