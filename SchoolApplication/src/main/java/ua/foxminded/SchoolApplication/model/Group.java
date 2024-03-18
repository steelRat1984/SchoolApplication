package ua.foxminded.SchoolApplication.model;

import java.util.List;
import java.util.Objects;

public class Group {

	private int groupID;
	private String groupName;
	private List<Student> studentsInGroup;
	
	public Group(int groupID, String groupName, List<Student> studentsInGroup) {
		this.groupID = groupID;
		this.groupName = groupName;
		this.studentsInGroup = studentsInGroup;
	}

	public Group(int groupID, String groupName) {
		this.groupID = groupID;
		this.groupName = groupName;
	}
	
	public Group() {
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Student> getStudentsInGroup() {
		return studentsInGroup;
	}

	public void setStudentsInGroup(List<Student> studentsInGroup) {
		this.studentsInGroup = studentsInGroup;
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupID, groupName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return groupID == other.groupID && Objects.equals(groupName, other.groupName);
	}
}
