package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.SchoolApplication.model.Group;

public class GroupMapper implements RowMapper<Group> {

	public static Group map(ResultSet resultSet) throws SQLException {
		int groupId = resultSet.getInt("group_id");
		String groupName = resultSet.getString("group_name").trim();
		return new Group(groupId, groupName);
	}

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		int groupId = rs.getInt("group_id");
		String groupName = rs.getString("group_name").trim();	
		return new Group(groupId, groupName);
	}
}
