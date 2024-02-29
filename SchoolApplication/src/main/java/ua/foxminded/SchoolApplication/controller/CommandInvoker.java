package ua.foxminded.SchoolApplication.controller;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
	private final Map<Integer, Command> commands = new HashMap<>();

	public void register(int commandId, Command command) {
		commands.put(commandId, command);
	}	

	public void executeCommand(int commandId) {
		Command command = commands.get(commandId);
		if (command != null) {
			command.execute();
		} else {
			System.out.println("The command was not found by the identifier: " + commandId);
		}
	}
	public void showCommandsDescriopion () {
		System.out.println("avaible commands :");
		for (int commandId : commands.keySet()) {
			String description = commands.get(commandId).getDescription();
			System.out.println(commandId + ". " + description );
		}
	}
	
}
