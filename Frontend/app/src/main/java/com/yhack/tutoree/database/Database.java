package com.yhack.tutoree.database;
// package uk.ac.cam.jk810.yhack20;

import android.annotation.SuppressLint;

import com.yhack.tutoree.database.model.Person;
import com.yhack.tutoree.database.model.Student;
import com.yhack.tutoree.database.model.Tutor;
import com.yhack.tutoree.database.model.dbexception.EntityNotFoundException;

import java.sql.Connection;
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

@SuppressLint("NewApi")
public class Database implements AutoCloseable {

    //    @Deprecated private final String url;
    private static final String getPeoples = "SELECT * FROM %s NATURAL JOIN Person where pid in (%s)";
    private static final String getName = "SELECT * FROM %s NATURAL JOIN Person where name like ?";
    private static final String getRating = "SELECT tid as pid, avg(rating) as rating FROM Teaches where tid = ?";
    private static final String getSubject = "SELECT * FROM Ability NATURAL JOIN Subject where pid = ?";
    private static final String getOffering = "SELECT * FROM Offering NATURAL JOIN Subject where pid = ?";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Deprecated
    private Connection conn;

//    /**
//     * Creates a database instance
//     *
//     * @param url the url of the db file
//     */
//    @Deprecated
//    public Database(String url) {
//        Database.url = url;
//    }

//    /**
//     * Must be called to initiate a connection before any other method. This can be
//     * used inside a try-with-resources block. Otherwise, {@link #close()} must be
//     * called once all transactions are done.
//     *
//     * @return The connection object for use inside a try-with-resources block
//     * @throws SQLException
//     */
//    @Deprecated
//    public Connection connect() throws SQLException {
//        conn = DriverManager.getConnection(url);
//        return conn;
//    }

    /**
     * Returns the details of a tutor by ID
     *
     * @param id
     * @return A Tutor object
     * @throws SQLException
     * @throws EntityNotFoundException if no tutor with this ID was found
     */
    public static Tutor getTutorByID(Connection conn, String id) throws SQLException, EntityNotFoundException {
        ArrayList<Tutor> result = getTutorsByIDs(conn, new ArrayList<String>(List.of(id)));
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
    public static ArrayList<Tutor> getTutorByName(Connection conn, String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(String.format(Database.getName, "Tutor"));
        ps.setString(1, "%" + name + "%");

        ResultSet rs = ps.executeQuery();

        ArrayList<String> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getString("pid"));
        }

        return getTutorsByIDs(conn, pids);
        // ArrayList<Tutor> results = new ArrayList<Tutor>();
        // while (rs.next()) {
        // Tutor tutor = new Tutor(rs.getString("pid"), rs.getString("name"),
        // rs.getString("gender"),
        // dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"),
        // rs.getString("school"),
        // rs.getInt("personality"), rs.getString("academics"),
        // rs.getBoolean("fulltime"));

        // ps = conn.prepareStatement(Database.getSubject);
        // ps.setString(1, tutor.getUsername());

        // ResultSet subjects = ps.executeQuery();

        // while (subjects.next()) {
        // tutor.getAbility().put(rs.getString("title"), rs.getDouble("score"));
        // }
        // results.add(tutor);
        // }
        // return results;
    }

    /**
     * Returns the details of a student by their ID
     *
     * @param id
     * @return The student object
     * @throws SQLException
     * @throws EntityNotFoundException if no student with this ID was found
     */
    public static Student getStudentByID(Connection conn, String id) throws SQLException, EntityNotFoundException {
        ArrayList<Student> result = getStudentsByIDs(conn, new ArrayList<String>(List.of(id)));
        if (result.size() == 0)
            throw new EntityNotFoundException("Student");
        else if (result.size() > 1)
            System.out.println("More than one result was retrived for id " + id);
        return result.get(0);
    }

    static ArrayList<Tutor> getTutorsByIDs(Connection conn, ArrayList<String> pids) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(Database.getPeoples, "Tutor",
                    String.join(",", pids.stream().map(x -> "?").collect(Collectors.toList()))));
            for (int i = 1; i <= pids.size(); i++) {
                ps.setString(i, pids.get(i - 1));
            }

            ResultSet rs = ps.executeQuery();

            Map<String, Tutor> map = new HashMap<>();
            while (rs.next()) {
                Tutor result = new Tutor(rs.getString("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("fulltime"));

                ps = conn.prepareStatement(Database.getSubject);
                ps.setString(1, result.getUsername());

                ResultSet rs2 = ps.executeQuery();

                while (rs2.next()) {
                    result.getAbility().put(rs2.getString("title"), rs2.getDouble("score"));
                }
                map.put(result.getUsername(), result);

                ps = conn.prepareStatement(Database.getRating);
                ps.setString(1, result.getUsername());

                rs2 = ps.executeQuery();

                while (rs2.next()) {
                    result.setRating(rs2.getDouble("rating"));
                }

                ps = conn.prepareStatement(Database.getOffering);
                ps.setString(1, result.getUsername());

                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    result.getOffering().add(rs2.getString("title"));
                }

            }
            ArrayList<Tutor> results = new ArrayList<>();
            for (String pid : pids) {
                results.add(map.get(pid));
            }
            return results;
        } catch (ParseException e) { // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    static ArrayList<Student> getStudentsByIDs(Connection conn, ArrayList<String> pids) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(Database.getPeoples, "Student",
                    String.join(",", pids.stream().map(x -> "?").collect(Collectors.toList()))));
            for (int i = 1; i <= pids.size(); i++) {
                ps.setString(i, pids.get(i - 1));
            }
            ResultSet rs = ps.executeQuery();

            Map<String, Student> map = new HashMap<>();
            while (rs.next()) {
                Student result = new Student(rs.getString("pid"), rs.getString("name"), rs.getString("gender"),
                        dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"), rs.getString("school"),
                        rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("optin"));

                ps = conn.prepareStatement(Database.getSubject);
                ps.setString(1, result.getUsername());

                ResultSet rs2 = ps.executeQuery();

                while (rs2.next()) {
                    result.getAbility().put(rs2.getString("title"), rs2.getDouble("score"));
                }
                map.put(result.getUsername(), result);

                ps = conn.prepareStatement(Database.getOffering);
                ps.setString(1, result.getUsername());

                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    result.getOffering().add(rs2.getString("title"));
                }
            }
            ArrayList<Student> results = new ArrayList<>();
            for (String pid : pids) {
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
    public static ArrayList<Tutor> getTopFilteredTutors(Connection conn, Map<String, Double> abilityScores, Double threshold, String gender,
                                                        String school, Boolean fulltime, int limit, List<String> offering) throws SQLException {
        String baseQuery = "SELECT * FROM Tutor NATURAL JOIN Person";
        String abilityJoin = "JOIN ability as a%d using (pid) join subject as s%d on a%d.sid = s%d.sid JOIN offering as o%d on Person.pid = o%d.pid and o%d.sid = s%d.sid";
        String subjectFilter = "s%d.title = ? and abs(a%d.score - ?)<?";
        String genderFilter = "gender = ?";
        String fulltimeFilter = "fulltime = ?";
        String schoolFilter = "school = ?";
        String randomOrder = "order by random() limit ?";

        String query = baseQuery;
        for (int i = 0; i < abilityScores.size(); i++) {
            query += " " + String.format(abilityJoin, i, i, i, i, i, i, i, i);
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

        ArrayList<String> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getString("pid"));
        }

        return getTutorsByIDs(conn, pids);

        // ArrayList<Tutor> results = new ArrayList<Tutor>();
        // while (rs.next()) {
        // Tutor tutor = new Tutor(rs.getString("pid"), rs.getString("name"),
        // rs.getString("gender"),
        // dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"),
        // rs.getString("school"),
        // rs.getInt("personality"), rs.getString("academics"),
        // rs.getBoolean("fulltime"));

        // ps = conn.prepareStatement(Database.getSubject);
        // ps.setString(1, tutor.getUsername());

        // ResultSet subjects = ps.executeQuery();

        // while (subjects.next()) {
        // tutor.getAbility().put(rs.getString("title"), rs.getDouble("score"));
        // }
        // results.add(tutor);
        // }
        // return results;
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
     * @throws SQLException
     */
    public static ArrayList<Student> getTopFilteredStudents(Connection conn, Map<String, Double> abilityScores, Double threshold, String gender,
                                                            String school, int limit) throws SQLException {
        String baseQuery = "SELECT * FROM Student NATURAL JOIN Person";
        String abilityJoin = "JOIN ability as a%d using (pid) join subject as s%d on a%d.sid = s%d.sid JOIN offering as o%d on Person.pid = o%d.pid and o%d.sid = s%d.sid";
        String subjectFilter = "s%d.title = ? and abs(a%d.score - ?)<?";
        String genderFilter = "gender = ?";
        String schoolFilter = "school = ?";
        String randomOrder = "order by random() limit ?";

        String query = baseQuery;
        for (int i = 0; i < abilityScores.size(); i++) {
            query += " " + String.format(abilityJoin, i, i, i, i, i, i, i, i);
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

        ArrayList<String> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getString("pid"));
        }

        return getStudentsByIDs(conn, pids);
        // ArrayList<Student> results = new ArrayList<Student>();
        // while (rs.next()) {
        // Student student = new Student(rs.getString("pid"), rs.getString("name"),
        // rs.getString("gender"),
        // dateFormat.parse(rs.getString("dob")), rs.getBoolean("verified"),
        // rs.getString("school"),
        // rs.getInt("personality"), rs.getString("academics"), rs.getBoolean("optin"));

        // ps = conn.prepareStatement(Database.getSubject);
        // ps.setString(1, student.getUsername());

        // ResultSet subjects = ps.executeQuery();

        // while (subjects.next()) {
        // student.getAbility().put(rs.getString("title"), rs.getDouble("score"));
        // }
        // results.add(student);
        // }
        // return results;
    }

    /**
     * Get the current tutors teaching the student
     *
     * @param studentID The id of the student
     * @return ArrayList<Tutor> - a list of tutors teaching the student
     * @throws SQLException
     * @throws EntityNotFoundException The given student id was not found
     */
    public static ArrayList<Tutor> getCurrentTutors(Connection conn, String studentID) throws SQLException, EntityNotFoundException {
        String teachingQuery = "select a.pid as pid from %s as a natural join Person join teaches as c on a.pid=c.%sid join %s as b on b.pid=c.%sid where b.pid = ?";
        PreparedStatement ps = conn.prepareStatement(String.format(teachingQuery, "Tutor", "t", "Student", "s"));
        ps.setString(1, studentID);

        ResultSet rs = ps.executeQuery();
        ArrayList<String> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getString("pid"));
        }

        return getTutorsByIDs(conn, pids);
    }

    /**
     * Get the current students taught by a tutor
     *
     * @param tutorID The id of the tutor
     * @return ArrayList<Student> - a list of students taught by the tutor
     * @throws SQLException
     * @throws EntityNotFoundException The given tutor was not found
     */
    public static ArrayList<Student> getCurrentStudents(Connection conn, String tutorID) throws SQLException, EntityNotFoundException {
        String teachingQuery = "select a.pid as pid from %s as a natural join Person join teaches as c on a.pid=c.%sid join %s as b on b.pid=c.%sid where b.pid = ?";
        PreparedStatement ps = conn.prepareStatement(String.format(teachingQuery, "Student", "s", "Tutor", "t"));
        ps.setString(1, tutorID);

        ResultSet rs = ps.executeQuery();
        ArrayList<String> pids = new ArrayList<>();
        while (rs.next()) {
            pids.add(rs.getString("pid"));
        }

        return getStudentsByIDs(conn, pids);
    }

    /**
     * Registers a new person to the database
     *
     * @param p The person object. Only the username and password are used for
     *          registration.
     * @return int The ID of the person
     * @throws SQLException
     */
    public static String registerPerson(Connection conn, Person p) throws SQLException {
        String insertStmt = "INSERT into Person (pid, password) VALUES (?,?)";

        conn.setAutoCommit(false);

        try {
            PreparedStatement ps = conn.prepareStatement(insertStmt);

            ps.setString(1, p.getUsername());
            ps.setString(2, p.getPassword());

            ps.executeUpdate();

            if (p instanceof Tutor) {
                String insertTutor = "INSERT into Tutor (pid, fulltime) VALUES (?,?)";
                PreparedStatement pstutor = conn.prepareStatement(insertTutor);

                pstutor.setString(1, p.getUsername());
                pstutor.setBoolean(2, ((Tutor) p).isFulltime());

                pstutor.executeUpdate();
            } else if (p instanceof Student) {
                String insertStudent = "INSERT into Student (pid, optin) VALUES (?,?)";
                PreparedStatement psstudent = conn.prepareStatement(insertStudent);

                psstudent.setString(1, p.getUsername());
                psstudent.setBoolean(2, ((Student) p).isOptin());

                psstudent.executeUpdate();
            }

            conn.commit();

            return p.getUsername();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * Modify an existing person in the database
     *
     * @param p The object describing the person. The ID will be used to identify
     *          which person to udpate.
     * @throws SQLException
     * @throws EntityNotFoundException
     */
    public static void modifyPerson(Connection conn, Person p) throws SQLException, EntityNotFoundException {
        String updateStmt = "UPDATE Person set name=?, gender=?, dob=?, verified=?, school=?, personality=?, academics=? where pid = ?";

        conn.setAutoCommit(false);

        try {
            PreparedStatement ps = conn.prepareStatement(updateStmt);

            ps.setString(1, p.getName());
            ps.setString(2, p.getGender());
            ps.setString(3, dateFormat.format(p.getDob()));
            ps.setBoolean(4, p.isVerified());
            ps.setString(5, p.getSchool());
            ps.setInt(6, p.getPersonality());
            ps.setString(7, p.getAcademics());

            ps.setString(8, p.getUsername());

            ps.executeUpdate();

            if (p instanceof Tutor) {
                String insertTutor = "UPDATE Tutor set fulltime = ? where pid = ?";
                PreparedStatement pstutor = conn.prepareStatement(insertTutor);

                pstutor.setBoolean(1, ((Tutor) p).isFulltime());
                pstutor.setString(2, p.getUsername());

                pstutor.executeUpdate();
            } else if (p instanceof Student) {
                String insertStudent = "UPDATE Student set optin = ? where pid = ?";
                PreparedStatement psstudent = conn.prepareStatement(insertStudent);

                psstudent.setBoolean(1, ((Student) p).isOptin());
                psstudent.setString(2, p.getUsername());

                psstudent.executeUpdate();
            }

            for (Entry<String, Double> score : p.getAbility().entrySet()) {
                addAbilities(conn, p, score.getKey(), score.getValue());
            }
            updateOffering(conn, p);

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * Updates the teaching status. This could be for changing likes, ratings or creating a new teaching status
     *
     * @param t      Tutor object
     * @param s      Student object
     * @param like   like boolean
     * @param rating int representing the rating. set to 0 for null
     * @throws SQLException
     */
    public static void updateTeachingStatus(Connection conn, Tutor t, Student s, boolean like, int rating) throws SQLException {
        String findTeaching = "SELECT chatid from Teaches where tid = ? and sid = ?";
        PreparedStatement ps = conn.prepareStatement(findTeaching);

        ps.setString(1, t.getUsername());
        ps.setString(2, s.getUsername());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ps = conn.prepareStatement("REPLACE INTO teaches VALUES (?,?,?,?,?)");
            ps.setInt(5, rs.getInt(1));
        } else {
            ps = conn.prepareStatement("INSERT INTO teaches (tid,sid,like,rating) VALUES (?,?,?,?)");
        }

        ps.setString(1, t.getUsername());
        ps.setString(2, s.getUsername());
        ps.setBoolean(3, like);
        ps.setInt(4, rating > 0 ? rating : null);

        ps.executeUpdate();
    }

    protected static void addAbilities(Connection conn, Person p, String subject, double score) throws SQLException {
        String insertAbility = "REPLACE INTO Ability SELECT ?, sid, ? from Subject where title = ?";
        PreparedStatement ps = conn.prepareStatement(insertAbility);

        ps.setString(1, p.getUsername());
        ps.setDouble(2, score);
        ps.setString(3, subject);

        ps.executeUpdate();
    }

    protected static void updateOffering(Connection conn, Person p) throws SQLException {
        final String insertOffering = "INSERT OR IGNORE INTO Offering SELECT ?, sid from Subject where title = ?";
        PreparedStatement ps = conn.prepareStatement(insertOffering);

        for (String subject : p.getOffering()) {
            ps.setString(1, p.getUsername());
            ps.setString(2, subject);
        }

        ps.executeUpdate();

        final String deleteOffering = "DELETE FROM Offering where pid = ? and sid not in (SELECT sid FROM Subject where title in (%s))";
        ps = conn.prepareStatement(String.format(deleteOffering, String.join(",", p.getOffering().stream().map(x -> "?").collect(Collectors.toList()))));

        for (int i = 1; i<=p.getOffering().size();i++) {
            ps.setString(i,p.getOffering().get(i-1));
        }

        ps.executeUpdate();
    }

    public static int getChatid(Connection conn, Tutor t, Student s) throws EntityNotFoundException, SQLException {
        final String getChat = "SELECT chatid from Teaches where tid = ? and sid = ?";
        PreparedStatement ps = conn.prepareStatement(getChat);

        ps.setString(1,t.getUsername());
        ps.setString(2,s.getUsername());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("chatid");
        }
        throw new EntityNotFoundException("Chat");
    }

    /**
     * Check login details
     * @param conn
     * @param username The username
     * @param password The password
     * @return True if login successful, false otherwise
     * @throws SQLException
     */
    public static boolean login(Connection conn, String username, String password) throws SQLException {
        final String login = "SELECT count(*) as num from Person where pid = ? and password = ?";

        PreparedStatement ps = conn.prepareStatement(login);
        ps.setString(1,username);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();

        rs.next();
        return rs.getInt("num") != 0;
    }
    /**
     * Closes the connection. Automatically called if used in a try-with-resources
     * block. Otherwise, must be called manually.
     *
     * @throws SQLException
     */
    @Override
    @Deprecated
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}