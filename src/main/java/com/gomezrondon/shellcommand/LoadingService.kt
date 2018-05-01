package com.gomezrondon.shellcommand

import org.apache.commons.text.StringSubstitutor
import java.io.File
import java.util.HashMap

class LoadingService {

    var values = HashMap<String, String>()

    constructor() {
        values.put("PW","/d/CA/Test/shell-command")
    }


    fun getOption(optionNumber: Int, filePath:String ): String {
        val option:Int = optionNumber
        val endOption = option.plus(1)

        val lines = File(filePath).readLines()
        val drop = lines.size.minus(lines.indexOf("--$endOption--"))
        val stringVal = lines.dropLast(drop).drop(lines.indexOf("--$option--").plus(1)).joinToString(separator = " && ")

        println("command: $stringVal")

        val sub = StringSubstitutor(values)
        return sub.replace(stringVal)
    }


}