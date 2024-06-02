-- Insert groups
INSERT INTO school_app.groups (group_name) VALUES ('Group A');
INSERT INTO school_app.groups (group_name) VALUES ('Group B');

-- Insert courses
INSERT INTO school_app.courses (course_name, course_description) VALUES ('Math', 'Mathematics Course');
INSERT INTO school_app.courses (course_name, course_description) VALUES ('Science', 'Science Course');

-- Insert students
INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (1, 'John', 'Doe');
INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (1, 'Jane', 'Smith');
INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (2, 'Emily', 'Johnson');

-- Insert student_courses
INSERT INTO school_app.students_courses (student_id, course_id) VALUES (1, 1);
INSERT INTO school_app.students_courses (student_id, course_id) VALUES (1, 2);
INSERT INTO school_app.students_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO school_app.students_courses (student_id, course_id) VALUES (3, 2);
