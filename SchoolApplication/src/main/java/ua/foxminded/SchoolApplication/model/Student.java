package ua.foxminded.SchoolApplication.model;

import java.util.List;

public class Student {
	private int studentID;
	private Group group;
	private String firstName;
	private String lastName;
	private List <Course> courses;
	

	public Student(int studentID, Group group, String firstName, String lastName, List<Course> courses) {
		this.studentID = studentID;
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
	}

	public Student () {
		
	}
	
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "student ID = " + studentID + "\n"+ " group № = " + group.getGroupID() + "\n"+ " first name = " + firstName + "\n"+ " last name = "
				+ lastName ;
	}
	
	
	
	
}
