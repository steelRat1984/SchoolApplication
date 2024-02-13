package ua.foxminded.SchoolApplication.controller;

import java.util.Map;
import java.util.Scanner;

import ua.foxminded.SchoolApplication.model.CourseName;
import ua.foxminded.SchoolApplication.model.Student;

public class Controller {
	private Scanner scanner;
	GroupStatisticReporter groupStatisticReporter = new GroupStatisticReporter();
	CourseFillingReporter courseFillingReporter = new CourseFillingReporter();
	StudentDAO studentManager = new StudentDAO();
	Map<Integer, CourseName> courses = courseFillingReporter.createCoursesMap();

	public Controller(Scanner scanner) {
		this.scanner = new Scanner(System.in);
	}

	public void run() {
		int courseNumber;
		int studentId;
		CourseName selectedCourse;
		boolean exitRequest = false;
		while (!exitRequest) {
			System.out.println("choise an option:");
			System.out.println("1. get report about number students in groups :");
			System.out.println("2. get list of students at the course");
			System.out.println("3. create a new student");
			System.out.println("4. delete a student");
			System.out.println("5. enroll student at the course");
			System.out.println("6. remove student at the course");
			System.out.println("0. EXIT");
			int choise = scanner.nextInt();
			switch (choise) {
			case 1:
				System.out.println(groupStatisticReporter.getGroupReport());
				break;
			case 2:
				System.out.println("choise a course number: ");
				for (Map.Entry<Integer, CourseName> entry : courses.entrySet()) {
					System.out.println(entry.getKey() + " - " + entry.getValue().getName());
				}
				courseNumber = scanner.nextInt();
				selectedCourse = courses.get(courseNumber);
				System.out.println(courseFillingReporter.getNamesFromCourse(selectedCourse));
				break;
			case 3:
				scanner.nextLine();
				System.out.println("Enter the student's firstname: ");
				String studentFirstname = scanner.nextLine();
				System.out.println("Enter the student's lastname: ");
				String studentLastname = scanner.nextLine();
				Student student = new Student(studentFirstname, studentLastname);
				studentManager.CreateAndInsertStudent(student);
				System.out.println("Student -" + studentFirstname + " " + studentLastname + " has been added" );
				break;
			case 4:
				System.out.println("enter the student's id : ");
				studentId = scanner.nextInt();
				studentManager.deleteStudentById(studentId);
				System.out.println("the student whose id - " + studentId + " has been delated");
				break;
			case 5:
				System.out.println("choise a course number: ");
				for (Map.Entry<Integer, CourseName> entry : courses.entrySet()) {
					System.out.println(entry.getKey() + " - " + entry.getValue().getName());
				}
				courseNumber = scanner.nextInt();
				selectedCourse = courses.get(courseNumber);
				System.out.println("Enter student's id :");
				studentId = scanner.nextInt();
				studentManager.addStudentToCourse(studentId, selectedCourse);
				System.out.println("the student whose id - " + studentId + " has been added to the " + selectedCourse.getName() + " course");
				break;
			case 6:
				System.out.println("choise a course number: ");
				for (Map.Entry<Integer, CourseName> entry : courses.entrySet()) {
					System.out.println(entry.getKey() + " - " + entry.getValue().getName());
				}
				courseNumber = scanner.nextInt();
				selectedCourse = courses.get(courseNumber);
				System.out.println("Enter student's id :");
				studentId = scanner.nextInt();
				studentManager.removeStudentFromCourse(studentId, selectedCourse);
				System.out.println("the student whose id - " + studentId + " has been remuved from the " + selectedCourse.getName() + " course");
				break;
			case 0:
				exitRequest = true;
				break;
			default:
				System.out.println("unknown command, try again");
			}

		}

	}
}
