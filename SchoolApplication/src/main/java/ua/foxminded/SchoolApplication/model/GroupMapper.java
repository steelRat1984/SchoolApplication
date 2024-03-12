package ua.foxminded.SchoolApplication.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper {
	
	public static Group map (ResultSet resultSet) throws SQLException{
		int groupId = resultSet.getInt("group_id");
		String name = resultSet.getString("group_name".trim());
		return new Group(groupId, name);
	}
}
