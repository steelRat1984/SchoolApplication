package ua.foxminded.SchoolApplication.commands;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.SchoolApplication.controller.Command;
import ua.foxminded.SchoolApplication.controller.CommandInvoker;

class CommandInvokerTest {
	private CommandInvoker commandInvoker;
	
	@Mock
	private Command mockCommand;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		commandInvoker = new CommandInvoker();
	}

	@Test
	public void executeCommand_WithValidCommandId_ShouldExecuteTheCommand() {
		commandInvoker.register(1, mockCommand);
		commandInvoker.executeCommand(1);
		verify(mockCommand).execute();
	}

	@Test
	public void executeCommand_WithInvalidCommandId_ShouldNotExecuteTheCommand() {
		commandInvoker.executeCommand(999);
		verify(mockCommand, never()).execute();
	}
}
