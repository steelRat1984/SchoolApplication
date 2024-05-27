package ua.foxminded.SchoolApplication.controller.commands;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.model.Group;
import ua.foxminded.SchoolApplication.service.GroupService;

@RequiredArgsConstructor
@Component
public class GetGroupByUnderOrEquelStudentNumber implements Command {
	
	private final GroupService groupServices;
	private final Scanner scanner;

	@Override
	public void execute() {
	
		System.out.println("Enter number of students:");
		int numberOfStudent = scanner.nextInt();
		StringBuilder result = new StringBuilder("here are the groups in which the number of students is equel or less than " + numberOfStudent +"\n");
		scanner.nextLine();
		List<Group> groups = groupServices.getGroupByNumberOfStudents(numberOfStudent);
		for (Group group : groups) {
			result.append(group.getGroupName() + "\n");
		}
		System.out.println(result.toString());
	}
//	public void execute() {
//		StringBuilder resultReport = new StringBuilder("Report on the number of students in groups:\n");
//		Map<Integer, List<Group>> groupsByStudentCount = groupServices.buildGroupReport();
//		groupsByStudentCount.entrySet().stream()
//				.sorted(Map.Entry.comparingByKey())
//				.forEach(entry -> {
//					int studentCount = entry.getKey();
//					List<Group> groups = entry.getValue();
//					if (studentCount > 0) {
//						resultReport.append("in group:");
//						for (Group group : groups) {
//							resultReport.append(group.getGroupID()).append(", ");
//						}
//						resultReport.delete(resultReport.length() - 2, resultReport.length());
//						resultReport.append(" studies ").append(studentCount).append(" students.\n");
//					}
//				});
//		System.out.println(resultReport.toString());
//
//	}

	@Override
	public String getDescription() {
		return "get groups in which the number of students is equal to or lower than your target number";
	}

}
