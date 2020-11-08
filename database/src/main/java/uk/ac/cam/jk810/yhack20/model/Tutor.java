package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Tutor extends Person {
    boolean fulltime;

    public Tutor(int id, String name, String gender, Date dob, boolean verified, String school, int personality,
            String academics, boolean fulltime) {
        super(id, name, gender, dob, verified, school, personality, academics);
        this.fulltime = fulltime;
    }

    public Tutor(String name, String gender, Date dob, boolean verified, String school, int personality,
            String academics, boolean fulltime) {
        super(name, gender, dob, verified, school, personality, academics);
        this.fulltime = fulltime;
    }

}
