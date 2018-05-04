package com.gomezrondon.shellcommand.service

import org.apache.commons.text.StringSubstitutor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File



@Service
class LoadingService {

    var values = HashMap<String, String>()

    @Value("\${shell-command.command-file}")
    lateinit var filePath:String

    constructor(@Value("\${shell-command.susbtitutor}") susbtitutor:Array<String>){

        if(susbtitutor.size == 4) {
            values.put("PW", susbtitutor[0])
            values.put("JRGM", susbtitutor[1])
            values.put("TEMP", susbtitutor[2])
            values.put("PATCH", susbtitutor[3])
        }
    }

    fun MutableMap<String, String>.add(key: String, value:String) = apply { put(key , value) }

    fun getLoadScripts(): Map<String, String>{
        val sub = StringSubstitutor(values)
        val str = sub.replace(File(filePath).readLines().joinToString("\n"))
        var map = mutableMapOf<String, String>()

        //get the menu numbers
        val regex ="""--(Menu|([\d.]+))--""".toRegex(RegexOption.MULTILINE)
        val regex2 ="""(Menu|([\d.]+))""".toRegex(RegexOption.MULTILINE)
        val menuNumbers = regex.findAll(str).map { regex2.find(it.value)}.map { it?.value }.toList()
//        println(menuNumbers.toString())

        //get the menu items
        val separate1 = str.split(regex).filter { it.isNotEmpty() }

        var count = 0
        menuNumbers.forEach {number ->
            map.put(number!!, separate1[count])
            count++
        }

        replaceCode(map)

        return map
    }


    fun replaceCode(commandMap: MutableMap<String, String>){

        for((key, value) in commandMap){
            val temp = value.trim()
            if(temp.contains("exec")){
                val split = temp.split(" ")[1].split(",")
                var expandCommand:String = ""
                for (number in split){
                    expandCommand += commandMap.get(number);
                }
                commandMap.add(key, expandCommand)
            }
        }
    }


}