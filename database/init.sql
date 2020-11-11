DROP TABLE IF EXISTS Ability;
DROP TABLE IF EXISTS Subject;
DROP TABLE IF EXISTS Teaches;
DROP TABLE IF EXISTS Tutor;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Person;

PRAGMA foreign_keys = ON; --for sqlite to enforce foreign key constraints

CREATE TABLE Person (
	pid VARCHAR(20) PRIMARY KEY,
    password CHAR(16),
	name VARCHAR(50),
    gender VARCHAR(10),
    dob DATE,
    verified BOOLEAN,
    school VARCHAR(30),
    personality INTEGER,
    academics TEXT
);

CREATE TABLE Tutor (
	pid VARCHAR(20) PRIMARY KEY,
    fulltime BOOLEAN,
    FOREIGN KEY (pid) REFERENCES Person (pid)
);

CREATE TABLE Student (
	pid VARCHAR(20) PRIMARY KEY,
    optin BOOLEAN,
    FOREIGN KEY (pid) REFERENCES Person (pid)
);

CREATE TABLE Teaches (
    tid VARCHAR(20),
    sid VARCHAR(20),
    like BOOLEAN,
    rating INTEGER,
    chatid INTEGER PRIMARY KEY,
    UNIQUE (tid, sid),
    FOREIGN KEY (tid) REFERENCES Tutor (pid),
    FOREIGN KEY (sid) REFERENCES Student (pid)
);

CREATE UNIQUE INDEX teaches_index
ON Teaches(tid,sid);

CREATE TABLE Subject (
    sid INTEGER PRIMARY KEY,
    title VARCHAR(20) UNIQUE
);

CREATE TABLE Ability (
	pid VARCHAR(20),
    sid INTEGER,
    score REAL,
    PRIMARY KEY (pid, sid)
);