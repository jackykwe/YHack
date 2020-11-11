package uk.ac.cam.jk810.yhack20.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores details about a person
 */
public class Person {
    String username; /** userid */
    String password; /** hashed password */
    String name; /** full name */
    String gender; /** gender of the person */
    Date dob; /** date of birth */
    boolean verified; /** whether the person has verified their identity */
    String school; /** school last studied at */
    int personality; /** 4-bits storing MBTI result */
    String academics; /** JSON string storing academic results */
    Map<String, Double> ability = new HashMap<>(); /** {@link Map} of subject to ML-generated ability score */

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

}
