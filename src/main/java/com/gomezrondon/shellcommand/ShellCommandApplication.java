package com.gomezrondon.shellcommand;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	@ShellMethod("Input number Option")
	public void command(String numberOption) {
		this.commandService.selectCommandOption(numberOption);
	}
}

@Service
class CommandService implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Files.lines(Paths.get("C:\\temp\\commands.txt")).
	}

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
		switch ( numerOption){

			case "1":
				selectCommandOption("2");
				selectCommandOption("4");
				break;
			case "1.1":
				selectCommandOption("3");
				selectCommandOption("2");
				selectCommandOption("4");
				break;
			case "1.2":
				selectCommandOption("3");
				selectCommandOption("2");
				break;
			case "2":
				command = Util.cd(PW);
				command = Util.CommandAndCommand(command,"docker-compose up -d");
				break;
			case "2.1":
				command = Util.cd(PW);
				command = Util.CommandAndCommand(command,"git apply /c/CA/Patchs/docker_db_es_sll_1.patch");
				command = Util.CommandAndCommand(command,"git apply /c/CA/Patchs/docker_db_es_sll_2.patch");
				break;
			case "99":
				command = Util.cd(PW);
				Path path = Paths.get("/c/CA", "Patchs/docker_db_es_sll_1.patch");

				break;
			case "2.2":
				command = Util.cd(PW);
				command = Util.CommandAndCommand(command,"git apply -R /c/CA/Patchs/docker_db_es_sll_1.patch");
				command = Util.CommandAndCommand(command,"git apply -R /c/CA/Patchs/docker_db_es_sll_2.patch");
				break;
			case "3":
				command = Util.cd(PW);
				command = Util.CommandAndCommand(command,"docker-compose down");
				break;

			case "4":
				command = getCommand4();
				builder.command("sh", "-c",command);
				break;
			case "5":
				command = Util.cd(PW);
				command = Util.CommandAndCommand(command,"gradle clean server:bootRun");

				break;
			case "6.1":
				command = Util.cd(PW);
				command = Util.CommandAndCommand(command,"git -c core.quotepath=false -c log.showSignature=false fetch origin --progress --prune");

				break;
			default:

		}

		builder.command("sh", "-c",command);
		System.out.println(command);

		try {
			Process start = builder.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(start.getInputStream()));
//			System.out.println(" >>>>>>>>>> sh11");
			stdInput.lines().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}




	}

	private String getCommand4() {
		String command;
		command = Util.cd(PW+"/db-migration");
		command = Util.CommandAndCommand(command,"gradle doMigration > "+TEMP+"/salida_migration.txt ");
		command = Util.CommandAndCommand(command,Util.cd(TEMP));
		command = Util.CommandAndCommand(command,"kotlinc -script validaMigrationv2.kts");
		return command;
	}


}
