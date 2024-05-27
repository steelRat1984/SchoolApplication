package ua.foxminded.SchoolApplication.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.dao.mappers.SingleStudentResultSetExtractor;
import ua.foxminded.SchoolApplication.dao.mappers.StudentResultSetExtractor;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@Repository
@RequiredArgsConstructor
public class StudentDAO {
	
	private final JdbcTemplate jdbcTemplate;
	private final StudentResultSetExtractor studentExtractor;
	private final SingleStudentResultSetExtractor singleStudentExtractor;

	public List<Student> getAllStudents() {
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id ";
		return jdbcTemplate.query(sql, studentExtractor);
	}

	public Student getStudentByName(String firstName, String lastName) {
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id "
				+ "WHERE s.first_name = ? AND s.last_name = ? "
				+ "ORDER BY c.course_id";

		return jdbcTemplate.query(sql, singleStudentExtractor, firstName, lastName);
	}

	public Student getStudentById(int studentId) {
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id "
				+ "WHERE s.student_id = ? "
				+ "ORDER BY c.course_id";
		return jdbcTemplate.query(sql, singleStudentExtractor, studentId);
	}

	public void deleteStudentById(int studentId) {
		String sql = "DELETE FROM school_app.students WHERE student_id = ?";
		jdbcTemplate.update(sql, studentId);
	}

	public boolean createStudent(Student student) {
		String sql = "INSERT INTO school_app.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql,
				student.getGroup().getGroupID(),
				student.getFirstName(),
				student.getLastName()) > 0;

	}

	public boolean deleteAssignments(int studentId, int courseId) {
		String sql = "DELETE FROM school_app.students_courses WHERE student_id = ? AND course_id = ?";
		return jdbcTemplate.update(sql, studentId, courseId) > 0;
	}

	public void deleteAllAssignmentsByStudentId(int studentId) {
		String sql = "DELETE FROM school_app.students_courses WHERE student_id = ?";
		jdbcTemplate.update(sql, studentId);
	}

	public void createAssignment(Student student) {
		String sql = "INSERT INTO school_app.students_courses (student_id, course_id) VALUES (?, ?)";
		List<Course> courses = student.getCourses();
		List<Object[]> parameters = new ArrayList<>();
		for (Course course : courses) {
			parameters.add(new Object[] { student.getStudentID(), course.getCourseID() });
		}
		jdbcTemplate.batchUpdate(sql, parameters);
	}
}
