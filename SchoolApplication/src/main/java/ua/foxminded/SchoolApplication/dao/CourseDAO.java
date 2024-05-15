package ua.foxminded.SchoolApplication.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.SchoolApplication.dao.mappers.CourseMapper;
import ua.foxminded.SchoolApplication.dao.mappers.StudentResultSetExtractor;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@Repository
public class CourseDAO {
	private final JdbcTemplate jdbcTemplate;
	private final CourseMapper courseMapper;
	private final StudentResultSetExtractor studentExtractor;
	
	@Autowired
	public CourseDAO(JdbcTemplate jdbcTemplate, CourseMapper courseMapper, StudentResultSetExtractor studentExtractor) {
		this.jdbcTemplate = jdbcTemplate;
		this.courseMapper = courseMapper;
		this.studentExtractor = studentExtractor;
	}


	public List<Course> getSelectedCoursesForStudent(int studentId) {
		String sql = "SELECT c.course_id, c.course_name, c.course_description FROM school_app.courses c "
				+ "JOIN school_app.students_courses sc ON c.course_id = sc.course_id WHERE sc.student_id = ?";
		return jdbcTemplate.query(sql, courseMapper, studentId);
	}

	public Course getCourseById(int courseId) {
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses WHERE course_id = ?";
		return jdbcTemplate.queryForObject(sql, courseMapper, courseId);
	}

	public List<Course> getAllCourses() {
		String sql = "SELECT course_id, course_name, course_description FROM school_app.courses";
		return jdbcTemplate.query(sql, courseMapper);
	}


	public List<Student> getStudentsOnCourse(int courseId) {
		String sql = "SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name, "
				+ "c.course_id, c.course_name, c.course_description "
				+ "FROM school_app.students s "
				+ "LEFT JOIN school_app.groups g ON s.group_id = g.group_id "
				+ "LEFT JOIN school_app.students_courses sc ON s.student_id = sc.student_id "
				+ "LEFT JOIN school_app.courses c ON sc.course_id = c.course_id "
				+ "WHERE c.course_id = ?";
		return jdbcTemplate.query(sql, studentExtractor, courseId);
	}

	public void createCourse(Course course) {
		String sql = "INSERT INTO school_app.courses (course_name, course_description) VALUES (?, ?)";
		jdbcTemplate.update(sql, course.getCourseName(), course.getCourseDescription());
	}

	
	

}
