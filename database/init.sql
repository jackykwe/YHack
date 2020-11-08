DROP TABLE IF EXISTS Ability;
DROP TABLE IF EXISTS Subject;
DROP TABLE IF EXISTS Teaches;
DROP TABLE IF EXISTS Tutor;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Person;

PRAGMA foreign_keys = ON; --for sqlite to enforce foreign key constraints

CREATE TABLE Person (
	pid INTEGER PRIMARY KEY,
	name VARCHAR(50),
    gender VARCHAR(10),
    dob DATE,
    verified BOOLEAN,
    school VARCHAR(30),
    personality INTEGER,
    academics TEXT
);

CREATE TABLE Tutor (
    pid INTEGER PRIMARY KEY,
    fulltime BOOLEAN,
    FOREIGN KEY (pid) REFERENCES Person (pid)
);

CREATE TABLE Student (
    pid INTEGER PRIMARY KEY,
    optin BOOLEAN,
    FOREIGN KEY (pid) REFERENCES Person (pid)
);

CREATE TABLE Teaches (
    tid INTEGER,
    sid INTEGER,
    like BOOLEAN,
    rating INTEGER,
    chatid INTEGER,
    PRIMARY KEY (tid, sid),
    FOREIGN KEY (tid) REFERENCES Person (pid),
    FOREIGN KEY (sid) REFERENCES Person (pid)
);

CREATE TABLE Subject (
    sid INTEGER PRIMARY KEY,
    title VARCHAR(20) UNIQUE
);

CREATE TABLE Ability (
    pid INTEGER,
    sid INTEGER,
    score REAL,
    PRIMARY KEY (pid, sid)
);