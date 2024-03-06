package ua.foxminded.SchoolApplication.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ua.foxminded.SchoolApplication.database.StudentDAO;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class GroupService {
	
	
	public Map <Integer, List<Group>> getDataforReport() {
		Map <Group, List<Student>> studentslistInEachGroup = getStudentslistInEachGroup();
		Map <Integer, List<Group>> groupsByStudentCount = new HashMap<>();
		studentslistInEachGroup.forEach((group, students) -> {
		    int studentCount = students.size();
		    groupsByStudentCount.computeIfAbsent(studentCount, k -> new ArrayList<>()).add(group);
		});
		return groupsByStudentCount;
	}

	private Map<Group, List<Student>> getStudentslistInEachGroup() {
	    StudentDAO studentDAO = new StudentDAO();
	    List<Student> allStudents = studentDAO.getAllStudents();
	    Map<Group, List<Student>> studentslistInEachGroup = new TreeMap<>(new Comparator<Group>() {
	    	@Override
	    	public int compare(Group o1, Group o2) {
	    		return Integer.compare(o1.getGroupID(), o2.getGroupID());
	    	}
		});
	    for (Student student : allStudents) {
	        Group group = student.getGroup();
	        studentslistInEachGroup.computeIfAbsent(group, k -> new ArrayList<>()).add(student);
	    }
	    return studentslistInEachGroup;
	}
}
