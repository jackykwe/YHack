package uk.ac.cam.jk810.yhack20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import uk.ac.cam.jk810.yhack20.model.Person;
import uk.ac.cam.jk810.yhack20.model.Student;
import uk.ac.cam.jk810.yhack20.model.Tutor;
import uk.ac.cam.jk810.yhack20.model.dbexception.EntityNotFoundException;

public class Database implements AutoCloseable {

    private Connection conn;
    private final String url;

    private final String getPeople = "SELECT * FROM %s NATURAL JOIN Person where pid = ?";
    private final String getPeoples = "SELECT * FROM %s NATURAL JOIN Person where pid in (%s)";
    private final String getName = "SELECT * FROM %s NATURAL JOIN Person where name like ?";
    private final String getSubject = "SELECT * FROM Ability NATURAL JOIN Subject where pid = ?";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a database instance
     * 
     * @param url the url of the db file
     */
    public Database(String url) {
        this.url = url;
    }

    /**
     * Must be called to initiate a connection before any other method. This can be
     * used inside a try-with-resources block. Otherwise, {@link #close()} must be
     * called once all transactions are done.
     * 
     * @return The connection object for use inside a try-with-resources block
     * @throws SQLException
     */
    public Connection connect() throws SQLException {
        conn = DriverManager.getConnection(url);
        return conn;
    }

    /**
     * Returns the details of a tutor by ID
     * 
     * @param id
     * @return A Tutor object
     * @throws SQLException
     * @throws EntityNotFoundException if no tutor with this ID was found
     */
    public Tutor getTutorByID(int id) throws SQLException, EntityNotFoundException {
        ArrayList<Tutor> result = getTutorsByIDs(new ArrayList<Integer>(List.of(id)));
        if (result.size() == 0)
            throw new EntityNotFoundException("Tutor");
        else if (result.size() > 1)
            System.out.println("More than one result was retrived for id " + id);
        return result.get(0);
    }

    /**
     * Searches and returns a list of tutors matching a name
     * 
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
     * 
     * @param id
     * @return The student object
     * @throws SQLException
     * @throws EntityNotFoundException if no student with this ID was found
     */
    public Student getStudentByID(int id) throws SQLException, EntityNotFoundException {
        ArrayList<Student> result = getStudentsByIDs(new ArrayList<Integer>(List.of(id)));
        if (result.size() == 0)
            throw new EntityNotFoundException("Student");
        else if (result.size() > 1)
            System.out.println("More than one result was retrived for id " + id);
        return result.get(0);
    }

    ArrayList<Tutor> getTutorsByIDs(ArrayList<Integer> pids) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getPeoples, "Tutor",
                    String.join(",", pids.stream().map(x -> Integer.toString(x)).collect(Collectors.toList()))));

            ResultSet rs = ps.executeQuery();

            Map<Integer,Tutor> map = new HashMap<>();
            while (rs.next()) {
                Tutor result = new Tutor(rs.getInt("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("fulltime"));

                ps = conn.prepareStatement(this.getSubject);
                ps.setInt(1, result.getId());

                ResultSet rs2 = ps.executeQuery();

                while (rs2.next()) {
                    result.getAbility().put(rs2.getString("title"), rs2.getDouble("score"));
                }
                map.put(result.getId(), result);
            }
            ArrayList<Tutor> results = new ArrayList<>();
            for (int pid : pids) {
                results.add(map.get(pid));
            }
            return results;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Student> getStudentsByIDs(ArrayList<Integer> pids) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(this.getPeoples, "Student",
                    String.join(",", pids.stream().map(x -> Integer.toString(x)).collect(Collectors.toList()))));

            ResultSet rs = ps.executeQuery();

            Map<Integer,Student> map = new HashMap<>();
            while (rs.next()) {
                Student result = new Student(rs.getInt("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("optin"));

                ps = conn.prepareStatement(this.getSubject);
                ps.setInt(1, result.getId());

                ResultSet rs2 = ps.executeQuery();

                while (rs2.next()) {
                    result.getAbility().put(rs2.getString("title"), rs2.getDouble("score"));
                }
                map.put(result.getId(), result);
            }
            ArrayList<Student> results = new ArrayList<>();
            for (int pid : pids) {
                results.add(map.get(pid));
            }
            return results;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a list of tutors filtered by some characteristics
     * 
     * @param abilityScores A map of subjects to ability scores that will be
     *                      compared to the tutors
     * @param threshold     The threshold above and below the ability score that is
     *                      acceptable
     * @param gender        Gender of the tutor. Can be null.
     * @param school        School of the tutor. Can be null.
     * @param fulltime      Whether the tutor is full-time. Can be null.
     * @param limit         Number of searches to return. Set to 0 to default to
     *                      100.
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
            for (int i = 0; i < abilityScores.size(); i++) {
                query += " " + String.format(abilityJoin, i, i, i, i);
            }

            query += " where ";

            ArrayList<String> queryFilters = new ArrayList<>();

            queryFilters.add("verified");

            for (int i = 0; i < abilityScores.size(); i++) {
                queryFilters.add(String.format(subjectFilter, i, i, i, i));
            }

            if (gender != null) {
                queryFilters.add(genderFilter);
            }
            if (fulltime != null) {
                queryFilters.add(fulltimeFilter);
            }
            if (school != null) {
                queryFilters.add(schoolFilter);
            }
            query += String.join(" and ", queryFilters);

            query += " " + randomOrder;
            System.out.println(query);

            PreparedStatement ps = conn.prepareStatement(query);

            {
                int i = 1;
                for (Entry<String, Double> subject : abilityScores.entrySet()) {
                    ps.setString(i++, subject.getKey());
                    ps.setDouble(i++, subject.getValue());
                    ps.setDouble(i++, threshold);
                }
                if (gender != null) {
                    ps.setString(i++, gender);
                }
                if (fulltime != null) {
                    ps.setBoolean(i++, fulltime);
                }
                if (school != null) {
                    ps.setString(i++, school);
                }
                ps.setInt(i++, limit <= 0 ? 100 : limit);
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

    /**
     * Returns a list of potential students
     * 
     * @param abilityScores Map of subjects to desired ability score
     * @param threshold     Threshold for accepting ability score
     * @param gender        Gender of the student. Can be null.
     * @param school        School of the student. Can be null.
     * @param limit         Number of searches to return. Setting to 0 will default
     *                      to 100.
     * @return An arraylist of tutors matching the filters
     * 
     * @throws SQLException
     */
    public ArrayList<Student> getTopFilteredStudents(Map<String, Double> abilityScores, Double threshold, String gender,
            String school, int limit) throws SQLException {
        try {
            String baseQuery = "SELECT * FROM Student NATURAL JOIN Person";
            String abilityJoin = "JOIN ability as a%d using (pid) join subject as s%d on a%d.sid = s%d.sid";
            String subjectFilter = "s%d.title = ? and abs(a%d.score - ?)<?";
            String genderFilter = "gender = ?";
            String schoolFilter = "school = ?";
            String randomOrder = "order by random() limit ?";

            String query = baseQuery;
            for (int i = 0; i < abilityScores.size(); i++) {
                query += " " + String.format(abilityJoin, i, i, i, i);
            }

            query += " where ";

            ArrayList<String> queryFilters = new ArrayList<>();

            queryFilters.add("verified"); // student should have verified
            queryFilters.add("optin"); // student should have opted in to being shown in recommendations to tutors

            for (int i = 0; i < abilityScores.size(); i++) {
                queryFilters.add(String.format(subjectFilter, i, i, i, i));
            }

            if (gender != null) {
                queryFilters.add(genderFilter);
            }
            if (school != null) {
                queryFilters.add(schoolFilter);
            }
            query += String.join(" and ", queryFilters);

            query += " " + randomOrder;
            System.out.println(query);

            PreparedStatement ps = conn.prepareStatement(query);

            {
                int i = 1;
                for (Entry<String, Double> subject : abilityScores.entrySet()) {
                    ps.setString(i++, subject.getKey());
                    ps.setDouble(i++, subject.getValue());
                    ps.setDouble(i++, threshold);
                }
                if (gender != null) {
                    ps.setString(i++, gender);
                }
                if (school != null) {
                    ps.setString(i++, school);
                }
                ps.setInt(i++, limit <= 0 ? 100 : limit);
            }

            ResultSet rs = ps.executeQuery();

            ArrayList<Student> results = new ArrayList<Student>();
            while (rs.next()) {
                Student student = new Student(rs.getInt("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("optin"));

                ps = conn.prepareStatement(this.getSubject);
                ps.setInt(1, student.getId());

                ResultSet subjects = ps.executeQuery();

                while (subjects.next()) {
                    student.getAbility().put(rs.getString("title"), rs.getDouble("score"));
                }
                results.add(student);
            }
            return results;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the current tutors teaching the student
     * 
     * @param studentID The id of the student
     * @return ArrayList<Tutor> - a list of tutors teaching the student
     * @throws SQLException
     * @throws EntityNotFoundException The given student id was not found
     */
    public ArrayList<Tutor> getCurrentTutors(int studentID) throws SQLException, EntityNotFoundException {
        String teachingQuery = "select a.pid as pid from %s as a natural join Person join teaches as c on a.pid=c.%sid join %s as b on b.pid=c.%sid where b.pid = ?";
        PreparedStatement ps = conn.prepareStatement(String.format(teachingQuery, "Tutor", "t", "Student", "s"));
        ps.setInt(1, studentID);

        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getInt("pid"));
        }

        return getTutorsByIDs(pids);
    }

    /**
     * Get the current students taught by a tutor
     * 
     * @param tutorID The id of the tutor
     * @return ArrayList<Student> - a list of students taught by the tutor
     * @throws SQLException
     * @throws EntityNotFoundException The given tutor was not found
     */
    public ArrayList<Student> getCurrentStudents(int tutorID) throws SQLException, EntityNotFoundException {
        String teachingQuery = "select a.pid as pid from %s as a natural join Person join teaches as c on a.pid=c.%sid join %s as b on b.pid=c.%sid where b.pid = ?";
        PreparedStatement ps = conn.prepareStatement(String.format(teachingQuery, "Student", "s", "Tutor", "t"));
        ps.setInt(1, tutorID);

        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getInt("pid"));
        }

        return getStudentsByIDs(pids);
    }

    /**
     * Add a new person to the database
     * 
     * @param p
     * @return int The ID of the person
     * @throws SQLException
     */
    public int addPerson(Person p) throws SQLException {
        return 0;
    }

    /**
     * Modify an existing person in the database
     * 
     * @param p The object describing the person. The ID will be used to identify
     *          which person to udpate.
     * @throws SQLException
     * @throws EntityNotFoundException
     */
    public void modifyPerson(Person p) throws SQLException, EntityNotFoundException {
    }

    /**
     * Closes the connection. Automatically called if used in a try-with-resources
     * block. Otherwise, must be called manually.
     * 
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}