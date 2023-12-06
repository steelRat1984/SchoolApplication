package ua.foxminded.SchoolApplication;

public class Course {
	private int courseID;
	private String courseName;
	private String courseDescription;
	
	public Course(int courseID, String courseName, String courseDescription) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
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
	
}
