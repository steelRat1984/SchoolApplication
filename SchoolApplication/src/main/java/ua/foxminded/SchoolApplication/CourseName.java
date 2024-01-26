package ua.foxminded.SchoolApplication;

public enum CourseName {
	HISTORY("History"),
	GEOGRAPHY("Geography"),
	CHEMISTRY("Chemistry"),
	PHYSICS("Physics"),
	BIOLOGY("Biology"),
	LITERATURE("Literature"),
	PHYSICAL_EDUCATION("Physical Education"),
	ART("Art"),
	MUSIC("Music"),
	COMPUTER_SCIENCE("Computer Science");

	private final String name;

	CourseName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
