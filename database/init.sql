DROP TABLE IF EXISTS Offering;
DROP TABLE IF EXISTS Ability;
DROP TABLE IF EXISTS Subject;
DROP TABLE IF EXISTS Teaches;
DROP TABLE IF EXISTS Tutor;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Person;

PRAGMA foreign_keys = ON; --for sqlite to enforce foreign key constraints

CREATE TABLE Person (
	pid VARCHAR(20) PRIMARY KEY NOT NULL,
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
	pid VARCHAR(20) PRIMARY KEY NOT NULL,
    fulltime BOOLEAN,
    FOREIGN KEY (pid) REFERENCES Person (pid)
);

CREATE TABLE Student (
	pid VARCHAR(20) PRIMARY KEY NOT NULL,
    optin BOOLEAN,
    FOREIGN KEY (pid) REFERENCES Person (pid)
);

CREATE TABLE Teaches (
    tid VARCHAR(20) NOT NULL,
    sid VARCHAR(20) NOT NULL,
    like BOOLEAN,
    rating INTEGER,
    chatid INTEGER PRIMARY KEY NOT NULL,
    UNIQUE (tid, sid),
    FOREIGN KEY (tid) REFERENCES Tutor (pid),
    FOREIGN KEY (sid) REFERENCES Student (pid)
);

CREATE UNIQUE INDEX teaches_index
ON Teaches(tid,sid);

CREATE TABLE Subject (
    sid INTEGER PRIMARY KEY NOT NULL,
    title VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Ability (
	pid VARCHAR(20) NOT NULL,
    sid INTEGER NOT NULL,
    score REAL,
    PRIMARY KEY (pid, sid)
);

CREATE TABLE Offering (
    pid VARCHAR(20) NOT NULL,
    sid INTEGER NOT NULL,
    PRIMARY KEY (pid, sid)
);

CREATE INDEX offers
ON Offering(pid);