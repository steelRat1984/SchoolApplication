package ua.foxminded.SchoolApplication.controller.commands;


import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.Services.GroupServices;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Group;

public class GetGroupStatisticReportCommand implements Command {
	private GroupServices groupServices = new GroupServices();

	public GetGroupStatisticReportCommand(GroupServices groupServices) {
		this.groupServices = groupServices;
	}

	@Override
	public void execute() {
		StringBuilder resultReport = new StringBuilder("Report on the number of students in groups:\n");
		Map<Integer, List<Group>> groupsByStudentCount = groupServices.getDataforReport();
		for (Map.Entry<Integer, List<Group>> entry : groupsByStudentCount.entrySet()) {
			int studentCount = entry.getKey();
			List<Group> groups = entry.getValue();
			if (studentCount > 0) {
				resultReport.append("in groups:");
				for (Group group : groups) {
					resultReport.append(group.getGroupID()).append(", ");
				}
				resultReport.delete(resultReport.length() - 2, resultReport.length());
				resultReport.append(" studies ").append(studentCount).append(" students.\n");
			}
		}
		System.out.println(resultReport.toString());
	}

}
