package ua.foxminded.SchoolApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DataGenerate {

	public List<String> generateFirstName() {
		List<String> firstName = new ArrayList<>();
		firstName.add("John");
		firstName.add("Petr");
		firstName.add("Maks");
		firstName.add("Roman");
		firstName.add("Felix");
		firstName.add("Serj");
		firstName.add("Michael");
		firstName.add("Anastasia");
		firstName.add("Sasha");
		firstName.add("Vlad");
		firstName.add("Alex");
		firstName.add("Viktor");
		firstName.add("Boris");
		firstName.add("Victoria");
		firstName.add("Helena");
		firstName.add("Vasyl");
		firstName.add("Tatjana");
		firstName.add("Lois");
		firstName.add("David");
		firstName.add("Yaroslav");
		return firstName;

	}

	public List<String> generateLastName() {
		List<String> lastName = new ArrayList<>();
		lastName.add("Bevz");
		lastName.add("Malun");
		lastName.add("Kryt");
		lastName.add("Smith");
		lastName.add("Bloha");
		lastName.add("Piskun");
		lastName.add("Kvach");
		lastName.add("Kobec");
		lastName.add("Makarenko");
		lastName.add("Reznik");
		lastName.add("Zorian");
		lastName.add("Chaplunka");
		lastName.add("Moroz");
		lastName.add("Sparow");
		lastName.add("Wolf");
		lastName.add("Vovk");
		lastName.add("MorningStar");
		lastName.add("Onikj");
		lastName.add("Skarlupka");
		lastName.add("Nikson");
		return lastName;
	}

	public List<Student> generateStudents() {
		List<Student> students = new ArrayList<>();
		List<String> firstName = generateFirstName();
		List<String> lastName = generateLastName();
		Random random = new Random();
		int firstNameIndex = 0;
		int lastNameIndex = 0;
		for (int i = 0; i < 200; i++) {

			Student student = new Student();
			student.setStudentID(i + 1);
			student.setGroupID(random.nextInt(10) + 1);
			student.setFirstName(firstName.get(firstNameIndex));
			student.setLastName(lastName.get(lastNameIndex));
			firstNameIndex++;
			if (firstNameIndex >= firstName.size()) {
				firstNameIndex = 0;
				lastNameIndex++;
			}
			students.add(student);
		}
		return students;
	}

	public List<Group> generateGoup() {
		List<Group> groups = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Group group = new Group();
			group.setGroupID(i + 1);
			group.setGroupName("group" + "_" + String.valueOf(i + 1));
			groups.add(group);
		}

		return groups;
	}

	public List<Course> generateCourse() {
		List<Course> courses = new ArrayList<>();
		Course courseHistory = new Course(1, CourseName.HISTORY.getName(), "learning History");
		Course courseGeography = new Course(2, CourseName.GEOGRAPHY.getName(), "learning Geography");
		Course courseChemistry = new Course(3, CourseName.CHEMISTRY.getName(), "learning Chemistry");
		Course coursePhysics = new Course(4, CourseName.PHYSICS.getName(), "learning Physics");
		Course courseBiology = new Course(5, CourseName.BIOLOGY.getName(), "learning Biology");
		Course courseLiterature = new Course(6, CourseName.LITERATURE.getName(), "learning Literature");
		Course coursePhysicalEducation = new Course(7, CourseName.PHYSICAL_EDUCATION.getName(), "learning Physical Education");
		Course courseArt = new Course(8, CourseName.ART.getName(), "learning Art");
		Course courseMusic = new Course(9, CourseName.MUSIC.getName(), "learning Music");
		Course courseComputerScience = new Course(10, CourseName.COMPUTER_SCIENCE.getName(), "learning Computer Science");

		courses.add(courseHistory);
		courses.add(courseGeography);
		courses.add(courseChemistry);
		courses.add(coursePhysics);
		courses.add(courseBiology);
		courses.add(courseLiterature);
		courses.add(coursePhysicalEducation);
		courses.add(courseArt);
		courses.add(courseMusic);
		courses.add(courseComputerScience);

		return courses;
	}

	public List<StudentCourseRelation> generateRelations(List<Student> students, List<Course> courses) {
		List<StudentCourseRelation> relations = new ArrayList<>();
		Random random = new Random();
		for (Student student : students) {
			int amountCourses = random.nextInt(3) + 1;
			Set<Integer> assignedCourses = new HashSet<>();
			while (assignedCourses.size() < amountCourses) {
				int eaachCourse = random.nextInt(courses.size());
				if (assignedCourses.add(courses.get(eaachCourse).getCourseID())) {
					StudentCourseRelation relation = new StudentCourseRelation();
					relation.setStudentID(student.getStudentID());
					relation.setCourseID(courses.get(eaachCourse).getCourseID());
					relations.add(relation);
				}
			}
		}
		return relations;
	}
}
