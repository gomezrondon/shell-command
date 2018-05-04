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
        var str = sub.replace(File(filePath).readLines().joinToString("\n"))
        var map = mutableMapOf<String, String>()

        //get the menu numbers
        val regex ="""--(Menu|([\d.]+))--""".toRegex(RegexOption.MULTILINE)
        val regex2 ="""(Menu|([\d.]+))""".toRegex(RegexOption.MULTILINE)
        val menuNumbers = regex.findAll(str).map { regex2.find(it.value)}.map { it?.value }.toList()

      //  findExecPlaceHolders(str)

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


    fun findExecPlaceHolders(str:String):MutableMap<String, String>{
        var execMap = mutableMapOf<String, String>()

        val regexExec ="""exec\s([\d.|,]+)""".toRegex(RegexOption.MULTILINE)
        val regexExec2 ="""([\d.|,]+)""".toRegex(RegexOption.MULTILINE)
        val execList = regexExec.findAll(str).map { it.value }.toList()

        //      execList.forEach { str = str.replace(it.toString(),"hola mundo!!") }

        val execNumers = regexExec.findAll(str).map { regexExec2.find(it.value)}.map { it!!.value }.toList()

        println(execNumers.toString())

        var count = 0
        execList.forEach {
            execMap.add(it,execNumers[count])
            count++
        }


        return execMap
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