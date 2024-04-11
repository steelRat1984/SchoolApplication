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

	public List<Group> getGroupByNumberOfStudents(int numberOfStudents) {
		List<Group> groups = new ArrayList<>();
		String sql = "SELECT g.* " +
				"FROM school_app.groups g " +
				"WHERE g.group_id IN ( " +
				"SELECT s.group_id " +
				"FROM school_app.students s " +
				"GROUP BY s.group_id " +
				"HAVING COUNT(s.student_id) <= ?)";

		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, numberOfStudents);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					groups.add(GroupMapper.map(resultSet));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groups;
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
			if (resultSet.next()) {
				group = GroupMapper.map(resultSet);
			}

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
