package ua.foxminded.SchoolApplication;


import java.util.List;

public class Main {

	public static void main(String[] args) {
/*
 * the first data filling
 */
		DataGenerate dataGenerate = new DataGenerate();
		DataBaseFiller dataBaseFiller = new DataBaseFiller();
		List<Student> students = dataGenerate.generateStudents();
		List<Group> groups = dataGenerate.generateGoup();
		List<Course> courses = dataGenerate.generateCourse();
		List<StudentCourseRelation> relations = dataGenerate.generateRelations(students, courses);
//		dataBaseFiller.fillingStudentsData(students);
//		dataBaseFiller.fillingGroupData(groups);
//		dataBaseFiller.fillingCourseData(courses);	
//		dataBaseFiller.fillingRelations(relations);
		SelectorNumberStudentsAndGroups selectorNumberStudentsAndGroups = new SelectorNumberStudentsAndGroups();
		System.out.println(selectorNumberStudentsAndGroups.selectNumberStudentsInGroups());
		SelectorNamesFromCourse selectorNamesFromGroups = new SelectorNamesFromCourse();
		System.out.println(selectorNamesFromGroups.selectNamesFromCourse(CourseName.COMPUTER_SCIENCE));
		StudentManipulations studentManipulations = new StudentManipulations();
//		Student newStudent = new Student();
//		newStudent.setFirstName("Anton");
//		newStudent.setLastName("Petrovchuk");
//		studentManipulations.CreateAndInsertStudent(newStudent);
		studentManipulations.addStudentToCourse(4, CourseName.COMPUTER_SCIENCE);

		

	}

}
