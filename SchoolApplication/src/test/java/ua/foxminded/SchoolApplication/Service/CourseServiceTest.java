package ua.foxminded.SchoolApplication.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.DAO.CourseDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

class CourseServiceTest {

	@Mock
	private CourseDAO courseDAO;

	@InjectMocks
	private CourseService courseService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void retyrnCourse_WhenCourseIdIsValid() {
		Course expectedCourse = new Course(1, "testCourse", "testDescription");
		when(courseDAO.getCourseById(1)).thenReturn(expectedCourse);
		Course actualCourse = courseService.getCourseById(1);
		assertEquals(expectedCourse, actualCourse);
	}

	@Test
	public void returnAllCourses_WhenMethodIsExecuted() {
		List<Course> expectedCourses = Arrays.asList(
				new Course(1, "name1", "descripteion1"),
				new Course(2, "name2", "descripteion2"),
				new Course(3, "name3", "descripteion3"),
				new Course(4, "name4", "descripteion4"),
				new Course(5, "name5", "descripteion5"),
				new Course(6, "name6", "descripteion6"),
				new Course(7, "name7", "descripteion7"));
		
		when(courseDAO.getAllCourses()).thenReturn(expectedCourses);
		List<Course> actualCourses = courseService.getAllCourses();
		
		assertEquals(expectedCourses.size(), actualCourses.size());
		assertEquals(expectedCourses, actualCourses);
		assertNotNull(actualCourses);
	}

	@Test
	public void returnStudents_forCertainCourseId() {
		Course course1 = new Course("course1", "description1");
		Course course2 = new Course("course2", "description2");
		List<Student> expectedStudents1 = Arrays.asList(
				new Student(1, new Group(1, "group_1"), "Vlad", "Slyvych", Arrays.asList(course1)),
				new Student(2, new Group(5, "group_5"), "Max", "Mad", Arrays.asList(course1)));
		List<Student> expectedStudents2 = Arrays.asList(
				new Student(1, new Group(1, "group_1"), "Mykola", "Fox", Arrays.asList(course2)),
				new Student(2, new Group(5, "group_5"), "Sasha", "Makar", Arrays.asList(course2)));
		
		when(courseService.getStudentsOnCourse(1)).thenReturn(expectedStudents1);
		when(courseService.getStudentsOnCourse(2)).thenReturn(expectedStudents2);
		List<Student> actualStudents1 = courseService.getStudentsOnCourse(1);
		List<Student> actualStudents2 = courseService.getStudentsOnCourse(2);
		
		assertEquals(expectedStudents1, actualStudents1);
		assertEquals(expectedStudents2, actualStudents2);
		assertEquals(expectedStudents1.size(), actualStudents2.size());
		assertEquals(expectedStudents2.size(), actualStudents2.size());
		assertNotEquals(expectedStudents2, actualStudents1);
		assertNotEquals(expectedStudents1, actualStudents2);
		
		
	}
}
