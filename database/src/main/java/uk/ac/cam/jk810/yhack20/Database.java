package uk.ac.cam.jk810.yhack20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import uk.ac.cam.jk810.yhack20.model.Student;
import uk.ac.cam.jk810.yhack20.model.Tutor;
import uk.ac.cam.jk810.yhack20.model.dbexception.EntityNotFoundException;

public class Database {

    private Connection conn;
    private final String url;

    private final String getPeople = "SELECT * FROM %s NATURAL JOIN Person where pid = ?";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Database(String url) {
        this.url = url;
    }

    private <T> T connectAndRun(Function<Connection, T> query) throws SQLException {
        try {
            conn = DriverManager.getConnection(url);
            return query.apply(conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public Tutor getTutor(int id) throws SQLException, EntityNotFoundException {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getPeople, "Tutor"));
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                throw new EntityNotFoundException("Tutor");
            Tutor result = new Tutor(id, rs.getString("name"), dateFormat.parse(rs.getString("dob")),
                    rs.getBoolean("verified"), rs.getString("personality"), rs.getString("academics"));
            if (rs.next())
                System.err.println("More than one result was retrived for id " + id);
            return result;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    public Tutor getTutor9(int id) throws SQLException {
        return connectAndRun((conn) -> {
            try {
                Statement ps = conn.createStatement();

                ResultSet rs = ps.executeQuery("SELECT * FROM Tutor NATURAL JOIN Person where pid = 9");

                if (!rs.next())
                    return null;
                Tutor result = new Tutor(id, rs.getString("name"), dateFormat.parse(rs.getString("dob")),
                        rs.getBoolean("verified"), rs.getString("personality"), rs.getString("academics"));
                if (rs.next())
                    System.err.println("More than one result was retrived for id " + id);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public Student getStudent(int id) throws SQLException, EntityNotFoundException {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getPeople, "Student"));
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                throw new EntityNotFoundException("Student");
            Student result = new Student(id, rs.getString("name"), dateFormat.parse(rs.getString("dob")),
                    rs.getBoolean("verified"), rs.getString("personality"), rs.getString("academics"),
                    rs.getBoolean("optin"));
            if (rs.next())
                System.err.println("More than one result was retrived for id " + id);
            return result;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

}
