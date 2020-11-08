package uk.ac.cam.jk810.yhack20;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            ArrayList<Tutor> tutors = db.getTopFilteredTutors(abilityScores, 0.2, "Female", "ACSI", true,0);
            assertTrue(tutors.stream().map((t)->t.getName()).anyMatch(name->name.equals("Hariot")));
        }
    }
}
