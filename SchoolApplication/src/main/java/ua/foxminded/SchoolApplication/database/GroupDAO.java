package ua.foxminded.SchoolApplication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.model.Group;

public class GroupDAO {

	public List<Group> selectActualListOfGroups() {
		List<Group> actualListOfGroups = new ArrayList<>();
		String sql = "SELECT group_id, group_name FROM school_app.groups";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int groupId = resultSet.getInt("group_id");
				String groupName = resultSet.getString("group_name");
				Group group = new Group(groupId, groupName);
				actualListOfGroups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actualListOfGroups;
	}

	public Group getGroupById(int groupId) {
		Group group = new Group();
		String selectSQL = "SELECT group_id, group_name FROM school_app.groups WHERE group_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setInt(1, groupId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int id = resultSet.getInt("group_id");
					String name = resultSet.getString("group_name");
					group.setGroupID(id);
					group.setGroupName(name);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

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
