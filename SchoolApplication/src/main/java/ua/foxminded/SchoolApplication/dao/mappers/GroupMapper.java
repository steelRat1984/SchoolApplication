package ua.foxminded.SchoolApplication.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.SchoolApplication.model.Group;

@Component
public class GroupMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		int groupId = rs.getInt("group_id");
		String groupName = rs.getString("group_name").trim();	
		return new Group(groupId, groupName);
	}
}
