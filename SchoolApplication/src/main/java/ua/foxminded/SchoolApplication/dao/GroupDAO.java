package ua.foxminded.SchoolApplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.dao.mappers.GroupMapper;
import ua.foxminded.SchoolApplication.model.Group;

public class GroupDAO {
	public List<Group> getAllGroups() {
		List<Group> allGroups = new ArrayList<>();
		String sql = "SELECT g.group_id, g.group_name, s.student_id, s.first_name, s.last_name "
				+ "FROM school_app.groups g "
				+ "LEFT JOIN school_app.students s ON g.group_id = s.group_id "
				+ "ORDER BY g.group_id, s.student_id"; 
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
					allGroups = GroupMapper.map(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allGroups;
	}

	public Group getGroupById(int groupId) {
		Group group = new Group();
		String sql = "SELECT g.group_id, g.group_name, s.student_id, s.first_name, s.last_name "
				+ "FROM school_app.groups g "
				+ "LEFT JOIN school_app.students s ON g.group_id = s.group_id "
				+ "ORDER BY g.group_id, s.student_id"; 
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
					group = GroupMapper.map(resultSet).get(0);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	public void createGroup(Group group) {
		String sql = "INSERT INTO school_app.groups (group_name) VALUES (?)";
		try (Connection connection = Database.connection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, group.getGroupName());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
