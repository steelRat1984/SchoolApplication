-- Insert groups
INSERT INTO testВb.groups (group_name) VALUES ('Group A');
INSERT INTO testВb.groups (group_name) VALUES ('Group B');

-- Insert courses
INSERT INTO testВb.courses (course_name, course_description) VALUES ('Math', 'Mathematics Course');
INSERT INTO testВb.courses (course_name, course_description) VALUES ('Science', 'Science Course');

-- Insert students
INSERT INTO testВb.students (group_id, first_name, last_name) VALUES ((SELECT group_id FROM testВb.groups WHERE group_name = 'Group A'), 'John', 'Doe');
INSERT INTO testВb.students (group_id, first_name, last_name) VALUES ((SELECT group_id FROM testВb.groups WHERE group_name = 'Group A'), 'Jane', 'Smith');
INSERT INTO testВb.students (group_id, first_name, last_name) VALUES ((SELECT group_id FROM testВb.groups WHERE group_name = 'Group B'), 'Emily', 'Johnson');

-- Insert student_courses
INSERT INTO testВb.students_courses (student_id, course_id) VALUES ((SELECT student_id FROM testВb.students WHERE first_name = 'John' AND last_name = 'Doe'), (SELECT course_id FROM testВb.courses WHERE course_name = 'Math'));
INSERT INTO testВb.students_courses (student_id, course_id) VALUES ((SELECT student_id FROM testВb.students WHERE first_name = 'John' AND last_name = 'Doe'), (SELECT course_id FROM testВb.courses WHERE course_name = 'Science'));
INSERT INTO testВb.students_courses (student_id, course_id) VALUES ((SELECT student_id FROM testВb.students WHERE first_name = 'Jane' AND last_name = 'Smith'), (SELECT course_id FROM testВb.courses WHERE course_name = 'Math'));
INSERT INTO testВb.students_courses (student_id, course_id) VALUES ((SELECT student_id FROM testВb.students WHERE first_name = 'Emily' AND last_name = 'Johnson'), (SELECT course_id FROM testВb.courses WHERE course_name = 'Science'));
