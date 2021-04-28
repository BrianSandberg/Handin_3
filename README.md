# Handin_3

The SQL code for the database might not be included in this directory, so for good measure we have the code here in the ReadMe
The reason for this is unknown, but is hasn't worked for us during the entire project

DROP TABLE IF EXISTS Student; DROP TABLE IF EXISTS StudenID; DROP TABLE IF EXISTS Course;

CREATE TABLE IF NOT EXISTS Student( Name VARCHAR NOT NULL, StudentID integer primary key , City VARCHAR NOT NULL, Average integer );

CREATE TABLE IF NOT EXISTS Grade( CourseID integer, StudentID integer, Grade integer, FOREIGN KEY (CourseID) REFERENCES Course(CourseID) ON DELETE RESTRICT ON UPDATE CASCADE, FOREIGN KEY (StudentID) REFERENCES Student(StudentID) ON DELETE RESTRICT ON UPDATE CASCADE );

CREATE TABLE IF NOT EXISTS Course( Course VARCHAR NOT NULL, Teacher VARCHAR NOT NULL, CourseID integer primary key, Average integer );

INSERT INTO Student(Name, StudentID, City, Average) VALUES ('Aisha Lincoln', 1, 'Nykøbing F', NULL), ('Anya Nielsen', 2, ' Nykøbing F', NULL), ('Alfred Jensen', 3, 'Karlskrona', NULL), ('Berta Bertelsen', 4, 'Billund', NULL), ('Albert Antonsen', 5, ' Sorø', NULL), ('Eske Eriksen', 6, 'Eskildstrup', NULL), ('Olaf Olesen', 7, 'Odense', NULL), ('Salma Simonsen', 8, 'Stockholm', NULL), ('Theis Thomasen', 9, 'Tølløse', NULL), ('Janet Jensen', 10, 'Jyllinge', NULL);

INSERT INTO Course(Course, Teacher, CourseID, Average) VALUES ('SD19', 'Line', 1, NULL), ('SD20', 'Line', 2, NULL), ('ES1-19', 'Ebbe', 3, NULL);

INSERT INTO Grade(CourseID, StudentID, Grade) VALUES (1, 1, 12), (3, 1, 10), (2, 2, NULL), (3, 2, 12), (1, 3, 7), (3, 3, 10), (2, 4, NULL), (3, 4, 2), (1, 5, 10), (3, 5, 7), (2, 6, NULL), (3, 6, 10), (1, 7, 4), (3, 7, 12), (2, 8, NULL), (3, 8, 12), (1, 9, 12), (3, 9, 12), (2, 10, NULL), (3, 10, 7);
