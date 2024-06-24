package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@RequiredArgsConstructor
@Component
public class StudentResultSetExtractor implements ResultSetExtractor<List<Student>> {

	private final CourseMapper courseMapper;
	private final StudentMapper studentMapper;

	@Override
	public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Integer, Student> students = new HashMap<>();
		while (rs.next()) {
			int studentId = rs.getInt("student_id");
			Student student = students.get(studentId);

			if (student == null) {
				student = studentMapper.mapRow(rs, rs.getRow());
				student.setCourses(new ArrayList<>());
				students.put(studentId, student);
			}

			int courseId = rs.getInt("course_id");
			if (!rs.wasNull()) {
				Course course = courseMapper.mapRow(rs, rs.getRow());
				student.getCourses().add(course);
			}
		}
		return new ArrayList<>(students.values());
	}
}
