package ua.foxminded.SchoolApplication.DAO.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class GroupMapper {

	public static List<Group> map(ResultSet resultSet) throws SQLException {
		List<Group> groups = new ArrayList<>();
		Group group = null;
		int lastGroupId = -1;

		while (resultSet.next()) {
			int currentGroupId = resultSet.getInt("group_id");
	
			if (currentGroupId != lastGroupId) {
				if (group != null) {
					groups.add(group);
				}
				String groupName = resultSet.getString("group_name").trim();
				group = new Group(currentGroupId, groupName, new ArrayList<>());
				lastGroupId = currentGroupId;
			}
			int studentId = resultSet.getInt("student_id");
			if (!resultSet.wasNull()) {
				String firstName = resultSet.getString("first_name").trim();
				String lastName = resultSet.getString("last_name").trim();
				Student student = new Student(studentId, group, firstName, lastName);
				group.getStudentsInGroup().add(student);
			}
		}
		if (group != null) {
			groups.add(group);
		}
		return groups;
	}

}
