package com.gomezrondon.shellcommand.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandService {

    @Autowired
    private final LoadingService lservice;

    private static Map<String, String> commands;

        public CommandService(LoadingService lservice){
            this.lservice = lservice;
            commands = this.lservice.getLoadScripts();
        }

    public String getMenu(){
        return commands.get("Menu");
    }

    public void selectCommandOption(String numberOption, String branch){
        String command = commands.get(numberOption);

        Map<String, String> map = new HashMap<>();
        map.put("branch",branch);
        String updCommand = lservice.replaceCode(map, command);
        System.out.println(updCommand);
        executeCommand(updCommand);
    }

    private void executeCommand(String command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("sh", "-c",command);
        try {
            Process start = builder.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(start.getInputStream()));
            stdInput.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(">>>>>>>> end ");
    }

}
