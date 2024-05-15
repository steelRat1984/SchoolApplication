package ua.foxminded.SchoolApplication.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.SchoolApplication.dao.mappers.GroupMapper;
import ua.foxminded.SchoolApplication.model.Group;

@Repository
public class GroupDAO {
	private final JdbcTemplate jdbcTemplate;
	private final GroupMapper groupMapper;
	
	@Autowired
	public GroupDAO(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.groupMapper = groupMapper;
	}

	public List<Group> getGroupByNumberOfStudents(int numberOfStudents) {
		String sql = "SELECT g.* " +
				"FROM school_app.groups g " +
				"WHERE g.group_id IN ( " +
				"SELECT s.group_id " +
				"FROM school_app.students s " +
				"GROUP BY s.group_id " +
				"HAVING COUNT(s.student_id) <= ?)";
		return jdbcTemplate.query(sql, groupMapper, numberOfStudents);
	}

	public Group getGroupById(int groupId) {
		String sql = "SELECT group_id, group_name FROM school_app.groups WHERE group_id = ?";
		return jdbcTemplate.queryForObject(sql, groupMapper, groupId);
	}

	public void createGroup(Group group) {
		String sql = "INSERT INTO school_app.groups (group_name) VALUES (?)";
		jdbcTemplate.update(sql, group.getGroupName());
	
	}
	

}
