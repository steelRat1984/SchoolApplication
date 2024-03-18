package ua.foxminded.SchoolApplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.SchoolApplication.DAO.mappers.GroupMapper;
import ua.foxminded.SchoolApplication.DAO.mappers.StudentMapper;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.model.Student;

public class GroupDAO {

	public List<Group> getAllGroups() {
		List<Group> allGroups = new ArrayList<>();
		String sql = "SELECT group_id, group_name FROM school_app.groups";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				allGroups.add(GroupMapper.map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allGroups;
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

	public List<Student> getStudentsInGroup(int groupId) {
		List<Student> students = new ArrayList<>();
		String sql = "SELECT student_id, first_name, last_name, group_id FROM school_app.students WHERE group_id = ?";
		try (Connection connection = Database.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, groupId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					students.add(StudentMapper.map(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
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
