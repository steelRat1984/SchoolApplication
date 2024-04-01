package ua.foxminded.SchoolApplication.model;

import java.util.Objects;

public class Course {
	private int courseID;
	private String courseName;
	private String courseDescription;

	public Course(String courseName, String courseDescription) {
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}
	public Course(int courseID, String courseName, String courseDescription) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}
	public Course () {
	}

	
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	@Override
	public int hashCode() {
		return Objects.hash(courseDescription, courseID, courseName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(courseDescription, other.courseDescription) && courseID == other.courseID
				&& Objects.equals(courseName, other.courseName);
	}


}
