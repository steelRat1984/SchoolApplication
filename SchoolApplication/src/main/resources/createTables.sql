
 DROP TABLE IF EXISTS school_app.students,school_app.groups, school_app.courses, school_app.students_groups_relations ;
-- Table: school_app.students
--DROP TABLE IF EXISTS school_app.students CASCADE;
CREATE TABLE IF NOT EXISTS school_app.students
(
    student_id integer PRIMARY KEY NOT NULL,
    group_id integer NOT NULL,
    first_name character(250) NOT NULL,
    last_name character(250) NOT NULL
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS school_app.students
    OWNER to school_admin;
    
    -- Table: school_app.groups
--DROP TABLE IF EXISTS school_app.groups;
CREATE TABLE IF NOT EXISTS school_app.groups
(
    group_id integer PRIMARY KEY NOT NULL,
    group_name character(250) NOT NULL
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS school_app.groups
    OWNER to school_admin;
    
    -- Table: school_app.courses
--DROP TABLE IF EXISTS school_app.courses CASCADE;
CREATE TABLE IF NOT EXISTS school_app.courses
(
    course_id integer PRIMARY KEY NOT NULL,
    course_name character(250) NOT NULL,
    course_description character(1000)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS school_app.courses
    OWNER to school_admin;
    
        -- Table: school_app.students_groups_relations
--DROP TABLE IF EXISTS school_app.students_groups_relations;
CREATE TABLE IF NOT EXISTS school_app.students_courses_relations
    (
    student_id int NOT NULL,
    course_id int NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES school_app.students(student_id),
    FOREIGN KEY (course_id) REFERENCES school_app.courses(course_id)
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS school_app.students_courses_relations
    OWNER to school_admin;