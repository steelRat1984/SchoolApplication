package ua.foxminded.SchoolApplication.model;

public class Course {
	private int courseID;
	private int numberOfStudents;
	private String courseName;
	private String courseDescription;
	
	public Course(int courseID, int numberOfStudents, String courseName, String courseDescription) {
		this.courseID = courseID;
		this.numberOfStudents = numberOfStudents;
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
	public int getNumberOfStudents() {
		return numberOfStudents;
	}
	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
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
