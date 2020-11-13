package com.yhack.tutoree.database.model;
// package uk.ac.cam.jk810.yhack20.model;

import java.io.Serializable;
import java.util.Date;

// implements Serializable to allow for use of this class in SafeArgs
public class Tutor extends Person implements Serializable {
    boolean fulltime;
    /**
     * whether the tutor teaches full time
     */
    double rating;

    /**
     * rating of the tutor
     */

    public Tutor(String username, String name, String gender, Date dob, boolean verified, String school,
                 int personality, String academics, boolean fulltime) {
        super(username, name, gender, dob, verified, school, personality, academics);
        this.fulltime = fulltime;
    }

    public Tutor(String username, String password) {
        super(username, password);
    }


    /**
     * @return boolean
     */
    public boolean isFulltime() {
        return fulltime;
    }


    /**
     * @param fulltime
     */
    public void setFulltime(boolean fulltime) {
        this.fulltime = fulltime;
    }


    /**
     * @return double
     */
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
