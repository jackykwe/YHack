package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;

public class Tutor extends Person {

    public Tutor(String name, Date dob, boolean verified, String personality, String academics) {
        super(name, dob, verified, personality, academics);
    }

    public Tutor(int id, String name, Date dob, boolean verified, String personality, String academics) {
        super(id, name, dob, verified, personality, academics);
    }
    
}
