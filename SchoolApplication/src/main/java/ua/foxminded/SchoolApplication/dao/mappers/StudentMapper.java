package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

@Component
public class StudentMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		int studentId = rs.getInt("student_id");
		String firstName = rs.getString("first_name").trim();
		String lastName = rs.getString("last_name").trim();
		Group group = new GroupMapper().mapRow(rs, rowNum);
		return new Student(studentId, group, firstName, lastName, new ArrayList<>());
	}
}
