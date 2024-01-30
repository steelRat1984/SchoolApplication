package ua.foxminded.SchoolApplication.database;

import java.util.List;

import ua.foxminded.SchoolApplication.model.Course;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;
import ua.foxminded.SchoolApplication.model.StudentCourseRelation;

public class DataBaseLoader {

	public void load() {
		DataGenerator dataGenerate = new DataGenerator();
		DatabaseInserter dataBaseFiller = new DatabaseInserter();

		List<Student> students = dataGenerate.generateStudents();
		List<Group> groups = dataGenerate.generateGoup();
		List<Course> courses = dataGenerate.generateCourse();
		List<StudentCourseRelation> relations = dataGenerate.generateRelations(students, courses);

		dataBaseFiller.insertStudentsData(students);
		dataBaseFiller.insertGroupData(groups);
		dataBaseFiller.insertCourseData(courses);
		dataBaseFiller.insertRelations(relations);

	}

}
