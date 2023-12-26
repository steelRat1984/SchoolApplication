package ua.foxminded.SchoolApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SelectorNumberStudentsAndGroups {

	public String selectNumberStudentsInGroups() {
		Map<Integer, Integer> numberOfStudentGroups = numberOfStudentGroups();
		Map<Integer, List<Integer>> groupByStudentCount = groupByStudentCount(numberOfStudentGroups);
		String listOfStudentsAndGroups = createResultForPrint(groupByStudentCount);
		return listOfStudentsAndGroups;

	}

	private Map<Integer, Integer> numberOfStudentGroups() {
		String sql = "SELECT group_id FROM school_app.students";
		Map<Integer, Integer> groupCounts = new HashMap<>();
		Connection connection = Database.connection();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int groupId = resultSet.getInt("group_id");
				groupCounts.put(groupId, groupCounts.getOrDefault(groupId, 0) + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groupCounts;
	}

	private Map<Integer, List<Integer>> groupByStudentCount(Map<Integer, Integer> numberOfStudents) {
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
						groupsIDsString.append(", ");
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
