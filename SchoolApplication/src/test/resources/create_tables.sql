CREATE TABLE IF NOT EXISTS testВb.students
(
    student_id SERIAL PRIMARY KEY,
    group_id integer,
    first_name character(250) NOT NULL,
    last_name character(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS testВb.groups
(
    group_id SERIAL PRIMARY KEY,
    group_name character(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS testВb.courses
(
    course_id SERIAL PRIMARY KEY,
    course_name character(250) NOT NULL,
    course_description character(1000)
);

CREATE TABLE IF NOT EXISTS testВb.students_courses
(
    student_id int NOT NULL,
    course_id int NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES testВb.students (student_id),
    FOREIGN KEY (course_id) REFERENCES testВb.courses (course_id)
);