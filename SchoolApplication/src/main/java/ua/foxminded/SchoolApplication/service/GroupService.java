package ua.foxminded.SchoolApplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.SchoolApplication.dao.GroupDAO;
import ua.foxminded.SchoolApplication.model.Group;

@RequiredArgsConstructor
@Service
public class GroupService {
	private final GroupDAO groupDAO;

	public Group getGroupById(int groupId) {
		return groupDAO.getGroupById(groupId);
	}

	public List<Group> getGroupByNumberOfStudents (int numberOfStudents){
		return groupDAO.getGroupByNumberOfStudents(numberOfStudents);
	}
	
	public void createGroup(Group group) {
		groupDAO.createGroup(group);
	}
}
