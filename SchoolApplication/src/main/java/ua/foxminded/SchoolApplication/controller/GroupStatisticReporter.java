package ua.foxminded.SchoolApplication.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ua.foxminded.SchoolApplication.database.Database;

public class GroupStatisticReporter {
	private final Connection connection = Database.connection();

	public String getGroupReport() {
		Map<Integer, Integer> numberOfStudentGroups = numberOfStudentGroups();
		Map<Integer, List<Integer>> groupByStudentCount = sortGroupBySize(numberOfStudentGroups);
		String listOfStudentsAndGroups = createResultForPrint(groupByStudentCount);
		return listOfStudentsAndGroups;

	}

	private Map<Integer, Integer> numberOfStudentGroups() {
		String sql = "SELECT group_id FROM school_app.students";
		Map<Integer, Integer> groupCounts = new HashMap<>();
		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int groupId = resultSet.getInt("group_id");
				groupCounts.put(groupId, groupCounts.getOrDefault(groupId, 0) + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groupCounts;
	}

	private Map<Integer, List<Integer>> sortGroupBySize(Map<Integer, Integer> numberOfStudents) {
		Map<Integer, List<Integer>> countGroups = new TreeMap<>();
		for (Map.Entry<Integer, Integer> entry : numberOfStudents.entrySet()) {
			int groupId = entry.getKey();
			int studentCount = entry.getValue();
			countGroups.computeIfAbsent(studentCount, k -> new ArrayList<>()).add(groupId);
		}
		return countGroups;
	}

	private String createResultForPrint(Map<Integer, List<Integer>> studentCountGroups) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<Integer, List<Integer>> entry : studentCountGroups.entrySet()) {
			List<Integer> groupIds = entry.getValue();
			if (!groupIds.isEmpty()) {
				StringBuilder groupsIDsString = new StringBuilder();
				for (int i = 0; i < groupIds.size(); i++) {
					groupsIDsString.append(groupIds.get(i));
					if (i < groupIds.size() - 1) {
						groupsIDsString.append(" and ");
					}
				}
				result.append("Group");
				result.append(" ");
				result.append(groupsIDsString);
				result.append(" ");
				result.append("has");
				result.append(" ");
				result.append(entry.getKey());
				result.append(" ");
				result.append("students");
				result.append("\n");

			}
		}

		return result.toString();
	}

}
