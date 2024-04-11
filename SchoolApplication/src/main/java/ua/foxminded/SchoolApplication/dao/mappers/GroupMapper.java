package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.foxminded.SchoolApplication.model.Group;

public class GroupMapper {

	public static Group map(ResultSet resultSet) throws SQLException {
		int groupId = resultSet.getInt("group_id");
		String groupName = resultSet.getString("group_name").trim();
		return new Group(groupId, groupName);
	}
}
