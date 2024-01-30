package ua.foxminded.SchoolApplication.main;


import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.CourseName;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.model.StudentCourseRelation;
import ua.foxminded.SchoolApplication.schoolController.CourseFillingReporter;
import ua.foxminded.SchoolApplication.schoolController.GroupStatisticReporter;
import ua.foxminded.SchoolApplication.schoolController.StudentManager;

public class Main {

	public static void main(String[] args) {
		GroupStatisticReporter selectorNumberStudentsAndGroups = new GroupStatisticReporter();
		System.out.println(selectorNumberStudentsAndGroups.getGroupReport());
		CourseFillingReporter selectorNamesFromGroups = new CourseFillingReporter();
		System.out.println(selectorNamesFromGroups.getNamesFromCourse(CourseName.COMPUTER_SCIENCE));
		StudentManager studentManipulations = new StudentManager();
//		Student newStudent = new Student();
//		newStudent.setFirstName("Anton");
//		newStudent.setLastName("Petrovchuk");
//		studentManipulations.CreateAndInsertStudent(newStudent);
//		studentManipulations.addStudentToCourse(4, CourseName.COMPUTER_SCIENCE);
		studentManipulations.removeStudentFromCourse(4, CourseName.COMPUTER_SCIENCE);
		

	}

}
