package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentResultSetExtractor implements ResultSetExtractor<List<Student>> {

    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentResultSetExtractor(CourseMapper courseMapper, StudentMapper studentMapper) {
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Student> idAndStudents = new TreeMap<>();
        while (rs.next()) {
            int studentId = rs.getInt("student_id");
            Student student = idAndStudents.get(studentId);

            if (student == null) {
                student = studentMapper.mapRow(rs, rs.getRow());
                student.setCourses(new ArrayList<>());
                idAndStudents.put(studentId, student);
            }

            int courseId = rs.getInt("course_id");
            if (!rs.wasNull()) {
                Course course = courseMapper.mapRow(rs, rs.getRow());
                student.getCourses().add(course);
            }
        }
        return new ArrayList<>(idAndStudents.values());
    }

}
