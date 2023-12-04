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
    
    
    -- Table: school_app.groups
-- DROP TABLE IF EXISTS school_app.groups;
CREATE TABLE IF NOT EXISTS school_app.groups
(
    group_id integer NOT NULL,
    goup_name character(250) COLLATE NOT NULL,
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