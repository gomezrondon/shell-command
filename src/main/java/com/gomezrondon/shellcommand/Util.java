package com.gomezrondon.shellcommand;

public class Util {



    public static String cd(String directory){
        return String.join(" ","cd", directory);
    }

    public static String CommandAndCommand(String comm1, String comm) {
        return String.join(" ",comm1, "&&", comm);
    }

    public static String CommandOrCommand(String comm1, String comm) {
        return String.join(" ",comm1, ";", comm);
    }
}
