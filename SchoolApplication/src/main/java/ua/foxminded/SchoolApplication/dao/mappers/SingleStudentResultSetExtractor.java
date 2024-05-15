package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

@Component
public class SingleStudentResultSetExtractor implements ResultSetExtractor<Student> {

    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public SingleStudentResultSetExtractor(CourseMapper courseMapper, StudentMapper studentMapper) {
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
        Student student = null;
        while (rs.next()) {
            if (student == null) {
                student = studentMapper.mapRow(rs, rs.getRow());
                student.setCourses(new ArrayList<>());
            }

            int courseId = rs.getInt("course_id");
            if (!rs.wasNull()) {
                Course course = courseMapper.mapRow(rs, rs.getRow());
                student.getCourses().add(course);
            }
        }
        return student;
    }
}
