package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Group;

public class GroupDAO {
	public void primaryinsertGroups(List<Group> groups) {
		String sql = "INSERT INTO school_app.groups (group_id, group_name) VALUES (?, ?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			for (Group group : groups) {
				statement.setInt(1, group.getGroupID());
				statement.setString(2, group.getGroupName());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
