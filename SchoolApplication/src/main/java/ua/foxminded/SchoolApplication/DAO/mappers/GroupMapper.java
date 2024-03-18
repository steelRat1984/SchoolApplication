package ua.foxminded.SchoolApplication.DAO.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.foxminded.SchoolApplication.model.Group;

public class GroupMapper {
	
	public static Group map (ResultSet resultSet) throws SQLException{
		int groupId = resultSet.getInt("group_id");
		String name = resultSet.getString("group_name".trim());
		return new Group(groupId, name);
	}
}
