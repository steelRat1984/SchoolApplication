package ua.foxminded.SchoolApplication;

public class StudentCourseRelation {
	private int studentID;
	private int courseID;
	
	public StudentCourseRelation(int studentID, int courseID) {
		this.studentID = studentID;
		this.courseID = courseID;
	}
	public StudentCourseRelation () {
		
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	
}
