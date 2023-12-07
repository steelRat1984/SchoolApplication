package ua.foxminded.SchoolApplication;

public class StudentCourseRelation {
	private int studentID;
	private int groupID;
	
	public StudentCourseRelation(int studentID, int groupID) {
		this.studentID = studentID;
		this.groupID = groupID;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
}
