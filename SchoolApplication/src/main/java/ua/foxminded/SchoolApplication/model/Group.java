package ua.foxminded.SchoolApplication.model;

import java.util.Objects;

public class Group {

	private int groupID;
	private String groupName;

	public Group() {
	}

	public Group(int groupID, String groupName) {
		this.groupID = groupID;
		this.groupName = groupName;
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
