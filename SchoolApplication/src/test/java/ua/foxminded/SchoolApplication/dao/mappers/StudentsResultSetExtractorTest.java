package ua.foxminded.SchoolApplication.dao.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

class StudentsResultSetExtractorTest {

	@Mock
	private ResultSet rs;

	@Mock
	private CourseMapper courseMapper;

	@Mock
	private StudentMapper studentMapper;

	@InjectMocks
	private StudentResultSetExtractor extractor;

	@BeforeEach
	public void setUp() throws SQLException {

		MockitoAnnotations.openMocks(this);

		when(rs.next()).thenReturn(true, true, true, true, false);

		// Встановлення значень для полів Student та Group для кожного студента
		when(rs.getInt("student_id")).thenReturn(1, 1, 2, 2);
		when(rs.getString("first_name")).thenReturn("John", "John", "Jack", "Jack");
		when(rs.getString("last_name")).thenReturn("Doe", "Doe", "Smith", "Smith");
		when(rs.getInt("group_id")).thenReturn(1, 1, 2, 2);
		when(rs.getString("group_name")).thenReturn("Group A", "Group A", "Group B", "Group B");

		// Встановлення значень для полів Course для кожного рядка
		when(rs.getInt("course_id")).thenReturn(1, 2, 3, 4);
		when(rs.getString("course_name")).thenReturn("Math", "Science", "History", "Geography");
		when(rs.getString("course_description")).thenReturn("Math course", "Science course", "History course",
				"Geography course");

		when(studentMapper.mapRow(any(ResultSet.class), anyInt())).thenAnswer(invocation -> {
			ResultSet rsMock = invocation.getArgument(0);
			int studentId = rsMock.getInt("student_id");
			String studentFirstName = rsMock.getString("first_name").trim();
			String studentLastName = rsMock.getString("last_name").trim();
			int groupId = rsMock.getInt("group_id");
			String groupName = rsMock.getString("group_name").trim();

			Group group = new Group(groupId, groupName);
			return new Student(studentId, group, studentFirstName, studentLastName, new ArrayList<>());
		});

		when(courseMapper.mapRow(any(ResultSet.class), anyInt())).thenAnswer(invocation -> {
			ResultSet rsMock = invocation.getArgument(0);
			int courseId = rsMock.getInt("course_id");
			String courseName = rsMock.getString("course_name").trim();
			String courseDescription = rsMock.getString("course_description").trim();
			return new Course(courseId, courseName, courseDescription);
		});

	}

    @Test
    public void testExtractData() throws SQLException {
        List<Student> actualStudents = extractor.extractData(rs);
        List<Course> courses = actualStudents.get(0).getCourses();
        assertNotNull(actualStudents);
        assertEquals(2, actualStudents.size());

        Student actualStudent1 = actualStudents.get(0);
//        assertNotNull(actualStudent1);
//        assertEquals(1, actualStudent1.getStudentID());
//        assertEquals("John", actualStudent1.getFirstName());
//        assertEquals("Doe", actualStudent1.getLastName());
//        assertEquals(2, actualStudent1.getCourses().size());

        Student actualStudent2 = actualStudents.get(1);
//        assertNotNull(actualStudent2);
//        assertEquals(2, actualStudent2.getStudentID());
//        assertEquals("Jack", actualStudent2.getFirstName());
//        assertEquals("Smith", actualStudent2.getLastName());
//        assertEquals(2, actualStudent2.getCourses().size());
        // Додаткові діагностичні повідомлення
        System.out.println("Student 1 Courses:");
        for (Course course : actualStudent1.getCourses()) {
            System.out.println(course.getCourseID() + ": " + course.getCourseName());
        }

        System.out.println("Student 2 Courses:");
        for (Course course : actualStudent2.getCourses()) {
            System.out.println(course.getCourseID() + ": " + course.getCourseName());
        }
        
        

    }
//	@Test
//	public void testExtractData() throws SQLException {
//		List<Student> actualStudents = extractor.extractData(rs);
//		Student actualStudent1 = actualStudents.get(0);
//		Student actualStudent2 = actualStudents.get(1);
//		Map<String, Course> courseMap1 = new HashMap<>();
//		for (Course course : actualStudent1.getCourses()) {
//			courseMap1.put(course.getCourseName(), course);
//		}
//		Course actualCourse1_1 = courseMap1.get("Math");
//		Course actualCourse1_2 = courseMap1.get("Science");
//		Map<String, Course> courseMap2 = new HashMap<>();
//		for (Course course : actualStudent2.getCourses()) {
//			courseMap2.put(course.getCourseName(), course);
//		}
//		Course actualCourse2_1 = courseMap2.get("History");
//		Course actualCourse2_2 = courseMap2.get("Geography");
//		
//		assertNotNull(actualStudents);
//		assertEquals(2, actualStudents.size());
//		assertNotNull(actualStudent1);
//		assertEquals(1, actualStudent1.getStudentID());
//		assertEquals("John", actualStudent1.getFirstName());
//		assertEquals("Doe", actualStudent1.getLastName());
//		assertEquals(2, actualStudent1.getCourses().size());
//
//		assertEquals("Math", actualCourse1_1.getCourseName());
//		assertEquals("Math course", actualCourse1_1.getCourseDescription());
//		assertEquals("Science", actualCourse1_2.getCourseName());
//		assertEquals("Science course", actualCourse1_2.getCourseDescription());
//
//		assertNotNull(actualStudent2);
//		assertEquals(2, actualStudent2.getStudentID());
//		assertEquals("Jack", actualStudent2.getFirstName());
//		assertEquals("Smith", actualStudent2.getLastName());
//		assertEquals(2, actualStudent2.getCourses().size());
//
//		assertEquals("History", actualCourse2_1.getCourseName());
//		assertEquals("History course", actualCourse2_1.getCourseDescription());
//		assertEquals("Geography", actualCourse2_2.getCourseName());
//		assertEquals("Geography course", actualCourse2_2.getCourseDescription());
//	}
//	


}
