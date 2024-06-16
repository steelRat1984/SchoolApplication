package ua.foxminded.SchoolApplication.service;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import ua.foxminded.SchoolApplication.model.Group;
@Testcontainers
@JdbcTest
@ComponentScan(basePackages = "ua.foxminded.SchoolApplication")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Sql(scripts = { "/create_schema.sql", "/create_tables.sql",
		"/insert_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/reset_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

class GroupServiceTest {

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3")
			.withDatabaseName("test")
			.withUsername("root")
			.withPassword("test");


	@Autowired
	private GroupService groupService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",
				() -> "jdbc:tc:postgresql:16.3:///test?currentSchema=school_app&TC_REUSABLE=true");
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Test
	void ShouldRturnGroupById() {
		Group expectedGroup = new Group(1, "Group A");
		Group actualGroup = groupService.getGroupById(1);
		assertEquals(expectedGroup, actualGroup);
		
	}

	@Test
	void ShoudReturnGroupByNumberOfStudents() {
		List<Group> groups= groupService.getGroupByNumberOfStudents(1);
		Group expectedGroup = new Group(2, "Group B");
		
		assertEquals(1, groups.size());
		assertTrue(groups.stream().anyMatch(group -> group.equals(expectedGroup)));
	}
	
	
	@Test
	void ShouldCreateGroup() {
		Group expectedGroup = new Group();
		expectedGroup.setGroupName("Group C");
		groupService.createGroup(expectedGroup);
		String sql = "SELECT group_id FROM school_app.groups WHERE group_name = ?"; 
		int groupId = jdbcTemplate.queryForObject(sql, Integer.class, expectedGroup.getGroupName());
		
		expectedGroup.setGroupID(groupId);
		Group actualGroup = groupService.getGroupById(groupId);
	
		assertEquals(expectedGroup, actualGroup);
	}
}
