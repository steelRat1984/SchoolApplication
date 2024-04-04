package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;
import java.util.Map;

import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.service.GroupService;

public class GetGroupReportCommand implements Command {
	private final GroupService groupServices;

	public GetGroupReportCommand(GroupService groupServices) {
		this.groupServices = groupServices;
	}

	@Override
	public void execute() {
		StringBuilder resultReport = new StringBuilder("Report on the number of students in groups:\n");
		Map<Integer, List<Group>> groupsByStudentCount = groupServices.buildGroupReport();
		groupsByStudentCount.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.forEach(entry -> {
					int studentCount = entry.getKey();
					List<Group> groups = entry.getValue();
					if (studentCount > 0) {
						resultReport.append("in group:");
						for (Group group : groups) {
							resultReport.append(group.getGroupID()).append(", ");
						}
						resultReport.delete(resultReport.length() - 2, resultReport.length());
						resultReport.append(" studies ").append(studentCount).append(" students.\n");
					}
				});
		System.out.println(resultReport.toString());

	}

	@Override
	public String getDescription() {
		return "get number of students in a group report";
	}

}
