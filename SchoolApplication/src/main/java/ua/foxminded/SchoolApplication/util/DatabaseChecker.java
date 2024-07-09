package ua.foxminded.SchoolApplication.util;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DatabaseChecker {
    private final JdbcTemplate jdbcTemplate;

    public boolean isDatabaseEmpty() {
        String sql = "SELECT "
                + " (SELECT COUNT(*) FROM school_app.students) as student_count, "
                + " (SELECT COUNT(*) FROM school_app.groups) as group_count, "
                + " (SELECT COUNT(*) FROM school_app.courses) as course_count, "
                + " (SELECT COUNT(*) FROM school_app.students_courses) as student_course_count";

        Map<String, Object> counts = jdbcTemplate.queryForMap(sql);

        int studentCount = ((Integer) counts.get("student_count")).intValue();
        int groupCount = ((Integer) counts.get("group_count")).intValue();
        int courseCount = ((Integer) counts.get("course_count")).intValue();
        int studentCourseCount = ((Integer) counts.get("student_course_count")).intValue();

        return studentCount == 0 && groupCount == 0 && courseCount == 0 && studentCourseCount == 0;
    }

}
