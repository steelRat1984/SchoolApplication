package ua.foxminded.SchoolApplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.dao.StudentDAO;
import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

class StudentServiceTest {
	private String firstName = "testFirstName";
	private String lastName = "testLastName";

	@Mock
	private StudentDAO studentDAO;

	@InjectMocks
	private StudentService studentService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void returnStudentWhemNameExist() {
		Student exeptedStudent = new Student(1, new Group(), firstName, lastName);
		when(studentDAO.getStudentByName(firstName, lastName)).thenReturn(exeptedStudent);
		Student actualStudent = studentService.getStudentByName(firstName, lastName);
		assertEquals(exeptedStudent, actualStudent);
		verify(studentDAO).getStudentByName(firstName, lastName);
	}

	@Test
	public void returnStudentWhenNameExist() {
		Student expectedStudent = new Student(1, new Group(), firstName, lastName);
		when(studentDAO.getStudentById(1)).thenReturn(expectedStudent);
		Student actualStudent = studentService.getStudentById(1);
		assertEquals(expectedStudent, actualStudent);
		verify(studentDAO).getStudentById(1);
	}

	@Test
	public void shouldEnrollStudentToNewCourse() {
		Student student = new Student();
		Course course1 = new Course(1, "Course1", "Description1");
		Course course2 = new Course(2, "Course2", "Description2");
		student.setCourses(Arrays.asList(course1));
		boolean result = studentService.enrollStudentToCourse(student, course2);
		assertTrue(result);
		verify(studentDAO).createAssignment(student);
		assertEquals(1, student.getCourses().size());
	}

	@Test
	public void shouldNotEnrollStudentToExistingCourse() {
		Student student = new Student();
		Course course1 = new Course(1, "Course1", "Description1");
		Course course2 = new Course(2, "Course2", "Description2");
		student.setCourses(Arrays.asList(course1, course2));
		boolean result = studentService.enrollStudentToCourse(student, course1);
		assertFalse(result);
		verify(studentDAO, never()).createAssignment(student);
		assertEquals(2, student.getCourses().size());
	}

	@Test
	public void shouldDeleteStudentFromCourse() {
		int studentId = 1;
		int courseId = 1;
		boolean result = false;
		when(studentDAO.deleteAssignments(studentId, courseId)).thenReturn(true);
		result = studentService.deletedStudentFromCourse(studentId, courseId);
		assertTrue(result);
		verify(studentDAO).deleteAssignments(studentId, courseId);
	}
	@Test
	public void shouldNotDeleteStudentFromCourse() {
		int studentId = 1;
		int courseId = 1;
		when(studentDAO.deleteAssignments(studentId, courseId)).thenReturn(false);
		boolean result = studentService.deletedStudentFromCourse(studentId, courseId);
		assertFalse(result);
		verify(studentDAO).deleteAssignments(studentId, courseId);
	}
	@Test
	public void shouldDelegateStudentDeletingToStudentDAO() {
		int studentId = 1; 
		studentService.deleteStudentById(studentId);
		verify(studentDAO).deleteStudentById(studentId);
		verify(studentDAO).deleteAllAssignmentsByStudentId(studentId);
	}
	@Test
	public void shouldCreateStudent() {
		Student student = new Student();
		when(studentDAO.createStudent(student)).thenReturn(true);
		boolean result = studentService.createStudent(student);
		assertTrue(result);
		verify(studentDAO).createStudent(student);
	}
	@Test
	public void shouldNotCreateStudent () {
		Student student = new Student();
		when(studentDAO.createStudent(student)).thenReturn(false);
		boolean result = studentService.createStudent(student);
		assertFalse(result);
		verify(studentDAO).createStudent(student);
	}
	
	@Test
	public void shouldDelegateAssigmentCreationToStudentDAO() {
		Student student = new Student();
		studentService.createAssignment(student);
		verify(studentDAO).createAssignment(student);
	}
	
}
