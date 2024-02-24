package ua.foxminded.SchoolApplication.controller;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
	private final Map<Integer, Command> commands = new HashMap<>();

	public void register(Integer commandId, Command command) {
		commands.put(commandId, command);
	}	

	public void executeCommand(Integer commandId) {
		Command command = commands.get(commandId);
		if (command != null) {
			command.execute();
		} else {
			System.out.println("The command was not found by the identifier: " + commandId);
		}
	}
}
