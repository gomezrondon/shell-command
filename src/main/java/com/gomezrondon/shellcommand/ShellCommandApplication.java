package com.gomezrondon.shellcommand;

import com.gomezrondon.shellcommand.service.CommandService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@SpringBootApplication
public class ShellCommandApplication{

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


	@ShellMethod(value="list all Menu Option", key = "m")
	public void menu(){
		System.out.println(commandService.getMenu());
	}

	@ShellMethod(value="Input number Option", key = "co")
	public void command(String numberOption) {
		this.commandService.selectCommandOption(numberOption);
	}
}






