package ua.foxminded.SchoolApplication.model;

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
}
