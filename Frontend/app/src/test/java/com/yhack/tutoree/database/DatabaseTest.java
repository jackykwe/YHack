package com.yhack.tutoree.database;

import com.yhack.tutoree.database.model.Person;
import com.yhack.tutoree.database.model.Student;
import com.yhack.tutoree.database.model.Tutor;
import com.yhack.tutoree.database.model.dbexception.EntityNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    public static void main(String[] args) throws SQLException, EntityNotFoundException, ParseException {
        new DatabaseTest().getStudentByID();
        new DatabaseTest().getTutorByIDs();
        new DatabaseTest().registerPerson();
    }

    @org.junit.jupiter.api.Test
    void getStudentByID() throws SQLException, EntityNotFoundException {
        try (
                Connection conn = DriverManager.getConnection("jdbc:sqldroid:test.db")) {
            Student s = Database.getStudentByID(conn,"e");
            assertEquals("Geography", s.getOffering().get(0));
        }
    }

    @org.junit.jupiter.api.Test
    void getTutorByIDs() throws SQLException, EntityNotFoundException {
        try (
                Connection conn = DriverManager.getConnection("jdbc:sqldroid:test.db")) {
            Tutor t = Database.getTutorByID(conn,"g");
            assertEquals(2,t.getOffering().size());
        }
    }

    @org.junit.jupiter.api.Test
    void registerPerson() throws SQLException, EntityNotFoundException, ParseException {

        try (
                Connection conn = DriverManager.getConnection("jdbc:sqldroid:test.db")) {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse("1999-05-10");
            Person p = new Student("k", "Karen", "Female", d, false, "Somewhere", 12, "", false);
            p.getAbility().put("English", 0.2);
            p.getAbility().put("Math", 0.7);
            p.getOffering().add("English");
            p.getOffering().add("Math");
            p.setPassword("randomhashed");
            Database.registerPerson(conn,p);
            Database.modifyPerson(conn,p);
            p.getOffering().remove(1);
            Database.modifyPerson(conn,p);
        }
    }

}