package ua.foxminded.SchoolApplication.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.foxminded.SchoolApplication.database.GroupDAO;

public class GroupMapper {
	
	public static Group map (ResultSet resultSet) throws SQLException{
		int groupId = resultSet.getInt("group_id");
		String name = resultSet.getString("group_name");
		int numberOfStudents = new GroupDAO().getStudentsInGroup(groupId).size();
		return new Group(groupId, name, numberOfStudents);
	}
}
