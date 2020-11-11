package uk.ac.cam.jk810.yhack20;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.cam.jk810.yhack20.model.Student;
import uk.ac.cam.jk810.yhack20.model.Tutor;
import uk.ac.cam.jk810.yhack20.model.dbexception.EntityNotFoundException;

public class DatabaseTest {

    String url = "jdbc:sqlite:test.db";
    Database db = new Database(url);
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void returnTutor() throws SQLException, EntityNotFoundException {
        try (Connection conn = db.connect()) {
            Tutor t = db.getTutorByID(9);
            assertEquals(9, t.getId());
            assertEquals("Jacky", t.getName());
            assertEquals("10/04/1999", df.format(t.getDob()));
            assertEquals(true, t.isVerified());
            assertEquals(13, t.getPersonality());
            assertEquals("{\"Computer Science\":\"A+\"}", t.getAcademics());
            assertEquals(0.9, t.getAbility().get("Computer Science"), 0.01);
            assertEquals(0.9, t.getAbility().get("Math"), 0.01);
        }
    }

    @Test
    public void returnStudent() throws SQLException, EntityNotFoundException {
        try (Connection conn = db.connect()) {
            Student t = db.getStudentByID(1);
            assertEquals(1, t.getId());
            assertEquals("Andrew", t.getName());
            assertEquals("02/10/1999", df.format(t.getDob()));
            assertEquals(false, t.isVerified());
            assertEquals(15, t.getPersonality());
            assertEquals("{\"Math\":\"A+\"}", t.getAcademics());
            assertEquals(0.5, t.getAbility().get("Math"), 0.01);
            assertEquals(0.4, t.getAbility().get("English"), 0.01);
            assertEquals(0.8, t.getAbility().get("Geography"), 0.01);
        }
    }

    @Test
    public void getFilteredTutors() throws SQLException {
        try (Connection conn = db.connect()) {
            Map<String, Double> abilityScores = new HashMap<>();
            abilityScores.put("Geography", 0.8);
            ArrayList<Tutor> tutors = db.getTopFilteredTutors(abilityScores, 0.2, "Female", "ACSI", true, 0);
            assertTrue(tutors.stream().map((t) -> t.getName()).anyMatch(name -> name.equals("Hariot")));
        }
    }

    @Test
    public void getFilteredStudents() throws SQLException {
        try (Connection conn = db.connect()) {
            Map<String, Double> abilityScores = new HashMap<>();
            abilityScores.put("Geography", 0.5);
            ArrayList<Student> students = db.getTopFilteredStudents(abilityScores, 0.4, null, null, 0);
            assertFalse("Not verified",
                    students.stream().map((t) -> t.getName()).anyMatch(name -> name.equals("Andrew")));
            assertFalse("Not opted in",
                    students.stream().map((t) -> t.getName()).anyMatch(name -> name.equals("Fredrick")));
            assertTrue(students.stream().map((t) -> t.getName()).anyMatch(name -> name.equals("Ellie")));
        }
    }

    @Test
    public void getTutorsByIDs() throws SQLException {
        try (Connection conn = db.connect()) {
            assertArrayEquals(new Object[] { 8, 7, 9 }, db.getTutorsByIDs(new ArrayList<Integer>(List.of(8, 7, 9)))
                    .stream().map(p -> p.getId()).collect(Collectors.toList()).toArray());
        }
    }

    @Test
    public void getStudentsByIDs() throws SQLException {
        try (Connection conn = db.connect()) {
            assertArrayEquals(new Object[] { 1, 5, 3 }, db.getStudentsByIDs(new ArrayList<Integer>(List.of(1, 5, 3)))
                    .stream().map(p -> p.getId()).collect(Collectors.toList()).toArray());
        }
    }

    @Test
    public void getTutorsForStudent() throws SQLException, EntityNotFoundException {
        try (Connection conn = db.connect()) {
            ArrayList<Tutor> tutors = db.getCurrentTutors(3);
            assertEquals(2, tutors.size());
            assertTrue(tutors.stream().map((t) -> t.getName()).anyMatch(name -> name.equals("Gary")));
            assertTrue(tutors.stream().map((t) -> t.getName()).anyMatch(name -> name.equals("Hariot")));
        }
    }

    @Test
    public void getStudentsForTeacher() throws SQLException, EntityNotFoundException {
        try (Connection conn = db.connect()) {
            ArrayList<Student> students = db.getCurrentStudents(8);
            assertEquals(5, students.size());
            assertArrayEquals(new String[] {"Andrew", "Betty", "Charles", "Ellie", "Fredrick"},  students.stream().map((t) -> t.getName()).sorted().collect(Collectors.toList()).toArray());
        }
    }
}
