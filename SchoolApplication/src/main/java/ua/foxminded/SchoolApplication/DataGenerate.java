package ua.foxminded.SchoolApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerate {
	
	public static void main(String[] args) {
		DataGenerate generate = new DataGenerate();
		List<Student> students = generate.generateStudents();
	}
	

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
			student.setGroupID(random.nextInt(11));
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
}
