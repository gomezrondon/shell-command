package com.gomezrondon.shellcommand;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		commands.put(5, "List music");
	}
	void selectCommandOption(int numerOption){
		System.out.println("Option selected: "+numerOption);
		ProcessBuilder builder = new ProcessBuilder();
		switch ( numerOption){
			case 5:
				//builder.command("cmd", "/c", "dir");
				builder.command("sh", "-c", "ls");
				try {
					Process start = builder.start();
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(start.getInputStream()));
					stdInput.lines().forEach(System.out::println);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(" >>>>>>>>>> sh");

				break;
			default:

		}



	}

}
