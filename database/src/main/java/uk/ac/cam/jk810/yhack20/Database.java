package uk.ac.cam.jk810.yhack20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.cam.jk810.yhack20.model.Student;
import uk.ac.cam.jk810.yhack20.model.Tutor;
import uk.ac.cam.jk810.yhack20.model.dbexception.EntityNotFoundException;

public class Database implements AutoCloseable {

    private Connection conn;
    private final String url;

    private final String getPeople = "SELECT * FROM %s NATURAL JOIN Person where pid = ?";
    private final String getName = "SELECT * FROM %s NATURAL JOIN Person where name like ?";
    private final String getSubject = "SELECT * FROM Ability NATURAL JOIN Subject where pid = ?";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a database instance
     * @param url the url of the db file
     */
    public Database(String url) {
        this.url = url;
    }

    /**
     * Must be called to initiate a connection before any other method.
     * This can be used inside a try-with-resources block. Otherwise,
     * {@link #close()} must be called once all transactions are done.
     * @return The connection object for use inside a try-with-resources block
     * @throws SQLException
     */
    public Connection connect() throws SQLException {
        conn = DriverManager.getConnection(url);
        return conn;
    }

    /**
     * Returns the details of a tutor by ID
     * @param id
     * @return A Tutor object
     * @throws SQLException
     * @throws EntityNotFoundException if no tutor with this ID was found
     */
    public Tutor getTutorByID(int id) throws SQLException, EntityNotFoundException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getPeople, "Tutor"));
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                throw new EntityNotFoundException("Tutor");
            Tutor result = new Tutor(id, rs.getString("name"), rs.getString("gender"),
                    dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                    rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("fulltime"));
            if (rs.next())
                System.err.println("More than one result was retrived for id " + id);

            ps = conn.prepareStatement(this.getSubject);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                result.getAbility().put(rs.getString("title"), rs.getDouble("score"));
            }
            return result;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Searches and returns a list of tutors matching a name
     * @param name The name to lookup
     * @return An arraylist of tutor objects
     * @throws SQLException
     */
    public ArrayList<Tutor> getTutorByName(String name) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getName, "Tutor"));
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            ArrayList<Tutor> results = new ArrayList<Tutor>();
            while (rs.next()) {
                Tutor tutor = new Tutor(rs.getInt("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("fulltime"));

                ps = conn.prepareStatement(this.getSubject);
                ps.setInt(1, tutor.getId());

                ResultSet subjects = ps.executeQuery();

                while (subjects.next()) {
                    tutor.getAbility().put(rs.getString("title"), rs.getDouble("score"));
                }
                results.add(tutor);
            }
            return results;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the details of a student by their ID
     * @param id
     * @return The student object
     * @throws SQLException
     * @throws EntityNotFoundException if no student with this ID was found
     */
    public Student getStudentByID(int id) throws SQLException, EntityNotFoundException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getPeople, "Student"));
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                throw new EntityNotFoundException("Student");
            Student result = new Student(id, rs.getString("name"), rs.getString("gender"),
                    dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                    rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("optin"));
            if (rs.next())
                System.err.println("More than one result was retrived for id " + id);

            ps = conn.prepareStatement(this.getSubject);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                result.getAbility().put(rs.getString("title"), rs.getDouble("score"));
            }
            return result;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a list of tutors filtered by some characteristics
     * @param abilityScores A map of subjects to ability scores that will be compared to the tutors
     * @param threshold The threshold above and below the ability score that is acceptable
     * @param gender Gender of the tutor. Can be null.
     * @param school School of the tutor. Can be null.
     * @param fulltime Whether the tutor is full-time. Can be null.
     * @param limit Number of searches to return. Set to 0 to default to 100.
     * @return An arraylist of tutors matching the filters
     * @throws SQLException
     */
    public ArrayList<Tutor> getTopFilteredTutors(Map<String, Double> abilityScores, Double threshold, String gender,
            String school, Boolean fulltime, int limit) throws SQLException {
        try {
            String baseQuery = "SELECT * FROM Tutor NATURAL JOIN Person";
            String abilityJoin = "JOIN ability as a%d using (pid) join subject as s%d on a%d.sid = s%d.sid";
            String subjectFilter = "s%d.title = ? and abs(a%d.score - ?)<?";
            String genderFilter = "gender = ?";
            String fulltimeFilter = "fulltime = ?";
            String schoolFilter = "school = ?";
            String randomOrder = "order by random() limit ?";

            String query = baseQuery;
            for (int i = 0; i<abilityScores.size(); i++) {
                query += " "+String.format(abilityJoin, i,i,i,i);
            }

            query += " where ";

            ArrayList<String> queryFilters = new ArrayList<>();

            queryFilters.add("verified");

            for (int i = 0; i<abilityScores.size(); i++) {
                queryFilters.add(String.format(subjectFilter, i,i,i,i));
            }

            if (gender!=null) {
                queryFilters.add(genderFilter);
            }
            if (fulltime!=null) {
                queryFilters.add(fulltimeFilter);
            }
            if (school!=null) {
                queryFilters.add(schoolFilter);
            }
            query += String.join(" and ",queryFilters);

            query += " "+randomOrder;
            System.out.println(query);

            PreparedStatement ps = conn.prepareStatement(query);

            {
                int i = 1;
                for (Entry<String,Double> subject : abilityScores.entrySet()) {
                    ps.setString(i++, subject.getKey());
                    ps.setDouble(i++, subject.getValue());
                    ps.setDouble(i++, threshold);
                }
                if (gender!=null) {
                    ps.setString(i++, gender);
                }
                if (fulltime!=null) {
                    ps.setBoolean(i++, fulltime);
                }
                if (school!=null) {
                    ps.setString(i++, school);
                }
                ps.setInt(i++, limit<=0?100:limit);
            }

            ResultSet rs = ps.executeQuery();

            ArrayList<Tutor> results = new ArrayList<Tutor>();
            while (rs.next()) {
                Tutor tutor = new Tutor(rs.getInt("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("fulltime"));

                ps = conn.prepareStatement(this.getSubject);
                ps.setInt(1, tutor.getId());

                ResultSet subjects = ps.executeQuery();

                while (subjects.next()) {
                    tutor.getAbility().put(rs.getString("title"), rs.getDouble("score"));
                }
                results.add(tutor);
            }
            return results;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    // public ArrayList<Tutor> getTopFilteredStudents() {

    // }

    /**
     * Closes the connection. Automatically called if used in a try-with-resources block.
     * Otherwise, must be called manually.
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}