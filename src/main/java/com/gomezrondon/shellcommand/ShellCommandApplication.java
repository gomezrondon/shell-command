package com.gomezrondon.shellcommand;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


	@ShellMethod("list all Option")
	public void option(){
		commandService.getCommandOptions().forEach(System.out::println);
	}

	@ShellMethod(value="Input number Option", key = "co")
	public void command(String numberOption) {
		this.commandService.selectCommandOption(numberOption);
	}
}

@Service
class CommandService {


	private final Map<String, String> commands = new HashMap<>();

	private final String PW;
	private final String JRGM;
	private final String TEMP;

	public CommandService(@Value("${shell-command.pw}") String pw
			,@Value("${shell-command.jrgm}") String jrgm
			,@Value("${shell-command.temp}") String temp){

		commands.put("1", "1) docker + Migration");
		commands.put("1.1", "        -1.1 docker down +docker up + Migration");
		commands.put("1.2", "        -1.2 toggle docker down + docker up");
		commands.put("2", "2) docker up");
		commands.put("2.1", "        -2.1 apply patch docker_db_es_sll7");
		commands.put("2.2", "        -2.2 revert patch docker_db_es_sll7");
		commands.put("3", "3) docker down");
		commands.put("4", "4) Migration");
		commands.put("5", "5) server:bootRun");
		commands.put("5.1", "        -5.1 server:build");
		commands.put("5.2", "        -5.2 move test report");
		commands.put("5.3", "        -5.3 switch branch to");
		commands.put("5.4", "        -5.4 server:build + generate report");
		commands.put("6", "6) UnStash docker");
		commands.put("6.1", "        -6.1 fetch from Origin");
		PW = pw;
		JRGM = jrgm;
		TEMP = temp;

	}

	public List<String> getCommandOptions(){
		List<String> list = new ArrayList<String>(commands.values());
		return list;
	}

	void selectCommandOption(String numerOption){

		ProcessBuilder builder = new ProcessBuilder();
		String command="";

		LoadingService lservice = new LoadingService();
		command =lservice.getOption(Integer.valueOf(numerOption),"C:\\temp\\commands.txt");
		System.out.println("java: "+command);
/*
		builder.command("sh", "-c",command);

		try {
			Process start = builder.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(start.getInputStream()));
			stdInput.lines().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
*/

	}

}
