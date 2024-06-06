CREATE TABLE IF NOT EXISTS school_app.students
(
    student_id SERIAL PRIMARY KEY,
    group_id integer,
    first_name character(250) NOT NULL,
    last_name character(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS school_app.groups
(
    group_id SERIAL PRIMARY KEY,
    group_name character(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS school_app.courses
(
    course_id SERIAL PRIMARY KEY,
    course_name character(250) NOT NULL,
    course_description character(1000)
);

CREATE TABLE IF NOT EXISTS school_app.students_courses
(
    student_id int NOT NULL,
    course_id int NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES school_app.students (student_id),
    FOREIGN KEY (course_id) REFERENCES school_app.courses (course_id)
);