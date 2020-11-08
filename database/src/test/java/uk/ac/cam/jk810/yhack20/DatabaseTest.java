package uk.ac.cam.jk810.yhack20;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
        Tutor t = db.getTutor(9);
        assertEquals(9, t.id);
        assertEquals("Jacky", t.name);
        assertEquals("10/04/1999",df.format(t.dob));
        assertEquals(true, t.verified);
        assertEquals("Smart", t.personality);
        assertEquals("{\"Computer Science\":\"A+\"}", t.academics);
    }

    @Test
    public void returnTutor9() throws SQLException {
        Tutor t = db.getTutor9(9);
        assertEquals(9, t.id);
        assertEquals("Jacky", t.name);
        assertEquals("10/04/1999",df.format(t.dob));
        assertEquals(true, t.verified);
        assertEquals("Smart", t.personality);
        assertEquals("{\"Computer Science\":\"A+\"}", t.academics);
    }

    @Test
    public void returnStudent() throws SQLException, EntityNotFoundException {
        Student t = db.getStudent(1);
        assertEquals(1, t.id);
        assertEquals("Andrew", t.name);
        assertEquals("02/10/1999",df.format(t.dob));
        assertEquals(false, t.verified);
        assertEquals("Happy", t.personality);
        assertEquals("{\"Math\":\"A+\"}", t.academics);
    }
}
