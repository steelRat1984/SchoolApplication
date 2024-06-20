package ua.foxminded.SchoolApplication.dao.mappers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class StudentResultSetExtractorTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private SingleStudentResultSetExtractor extractor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExtractData() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.wasNull()).thenReturn(false);

        when(resultSet.getInt("student_id")).thenReturn(1);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        
        when(resultSet.getInt("group_id")).thenReturn(1);
        when(resultSet.getString("group_name")).thenReturn("Group A");
        
        when(resultSet.getInt("course_id")).thenReturn(1, 2);
        when(resultSet.getString("course_name")).thenReturn("Math", "Science");
        when(resultSet.getString("course_description")).thenReturn("Math Course", "Science Course");
  
        Course mockedCourse1 = new Course(1, "Math", "Math Course");
        Course mockedCourse2 = new Course(2, "Science", "Science Course");
        Group mockedGroup = new Group(1, "Group A");
        Student mockedStudent = new Student(1, mockedGroup, "John", "Doe", Arrays.asList(mockedCourse1, mockedCourse2));

        

        when(studentMapper.mapRow(resultSet, 1)).thenReturn(mockedStudent);
        when(groupMapper.mapRow(resultSet, 1)).thenReturn(mockedGroup);
        when(courseMapper.mapRow(resultSet, 1)).thenReturn(mockedCourse1);
        when(courseMapper.mapRow(resultSet, 2)).thenReturn(mockedCourse2);

        Student actualStudent = extractor.extractData(resultSet);
            
        assertNotNull(actualStudent);
        assertNotNull(actualStudent.getCourses());
        assertEquals(2, actualStudent.getCourses().size());
        assertTrue(actualStudent.getCourses().contains(mockedCourse1));
        assertTrue(actualStudent.getCourses().contains(mockedCourse2));
        assertEquals(1, actualStudent.getGroup().getGroupID());
        assertEquals("Group A", actualStudent.getGroup().getGroupName());
    }
}
