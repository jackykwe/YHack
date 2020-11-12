package com.yhack.tutoree.database.model;
// package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Student extends Person {
    boolean optin;

    /**
     * whether the student has opted in to be automatically recommended to potential tutors
     */

    public Student(String username, String name, String gender, Date dob, boolean verified, String school,
                   int personality, String academics, boolean optin) {
        super(username, name, gender, dob, verified, school, personality, academics);
        this.optin = optin;
    }

    public Student(String username, String password) {
        super(username, password);
    }


    /**
     * @return boolean
     */
    public boolean isOptin() {
        return optin;
    }


    /**
     * @param optin
     */
    public void setOptin(boolean optin) {
        this.optin = optin;
    }

}
