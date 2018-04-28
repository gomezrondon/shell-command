package com.gomezrondon.shellcommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ShellCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShellCommandApplication.class, args);
	}
}

@ShellComponent
class shellCommand{

	private final CommandService commandService;

	shellCommand(CommandService commandService) {
		this.commandService = commandService;
	}

	@ShellMethod("Input numer Option")
	public void command(int numberOption) {
		this.commandService.selectCommandOption(numberOption);
	}
}

@Service
class CommandService{

	private final Map<Integer, String> commands = new HashMap<>();

	public CommandService(){
		commands.put(1, "docker + Migration");
		commands.put(2, "docker up");
		commands.put(3, "docker down");
		commands.put(4, "Migration");
	}
	void selectCommandOption(int numerOption){
		System.out.println("Option selected: "+numerOption);
	}

}
