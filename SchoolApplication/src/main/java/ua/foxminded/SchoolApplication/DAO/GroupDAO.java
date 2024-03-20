package ua.foxminded.SchoolApplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.DAO.mappers.GroupMapper;
import ua.foxminded.SchoolApplication.DAO.mappers.StudentMapper;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class GroupDAO {

	public List<Group> getAllGroups() {
		Map<Integer, Group> groupsMap = new HashMap<>();
		String sql = "SELECT g.group_id , g.group_name , s.student_id , s.first_name , s.last_name , s.group_id "
				+ "FROM school_app.groups g LEFT JOIN school_app.students s ON g.group_id = s.group_id";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				int groupId = resultSet.getInt("group_id");
				Group group = groupsMap.getOrDefault(groupId, new Group(groupId, resultSet.getString("group_name")));
				if (resultSet.getInt("student_id") > 0) {
					Student student = StudentMapper.map(resultSet);
					group.getStudentsInGroup().add(student);
				}
				groupsMap.putIfAbsent(groupId, group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(groupsMap.values());
	}

	public Group getGroupById(int groupId) {
		Group group = new Group();
		String selectSQL = "SELECT group_id, group_name FROM school_app.groups WHERE group_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setInt(1, groupId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					group = GroupMapper.map(resultSet);
				}
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
