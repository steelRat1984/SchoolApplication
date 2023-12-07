
 DROP TABLE IF EXISTS school_app.students,school_app.groups, school_app.courses, school_app.students_groups_relations ;


-- Table: school_app.students
-- DROP TABLE IF EXISTS school_app.students;
CREATE TABLE IF NOT EXISTS school_app.students
(
    student_id integer NOT NULL,
    group_id integer NOT NULL,
    first_name character(250) NOT NULL,
    last_name character(250) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id, group_id)
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS school_app.students
    OWNER to school_admin;
    
   -- makes studend_id uneque  
ALTER TABLE school_app.students ADD CONSTRAINT unique_student_id UNIQUE (student_id);
    


    -- Table: school_app.groups
--	DROP TABLE IF EXISTS school_app.groups;
CREATE TABLE IF NOT EXISTS school_app.groups
(
    group_id integer NOT NULL,
    group_name character(250) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS school_app.groups
    OWNER to school_admin;
    
    
    
    -- Table: school_app.courses
-- DROP TABLE IF EXISTS school_app.courses;
CREATE TABLE IF NOT EXISTS school_app.courses
(
    course_id integer NOT NULL,
    course_name character(250) NOT NULL,
    course_description character(1000),
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS school_app.courses
    OWNER to school_admin;
    
        -- Table: school_app.students_groups_relations
-- DROP TABLE IF EXISTS school_app.students_groups_relations;
    CREATE TABLE IF NOT EXISTS school_app.students_courses_relations
    (
 	student_id int REFERENCES school_app.students(student_id),
    course_id int REFERENCES school_app.courses(course_id),
    PRIMARY KEY (student_id, course_id)
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS school_app.school_app.students_courses_relations
    OWNER to school_admin;
    
    
    
    