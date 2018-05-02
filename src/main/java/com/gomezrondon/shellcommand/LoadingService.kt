package com.gomezrondon.shellcommand

import org.apache.commons.text.StringSubstitutor
import java.io.File
import java.util.HashMap


class LoadingService {

    var values = HashMap<String, String>()

    constructor() {
        values.put("PW","/c/CA/portfolio-workbench")
        values.put("JRGM","/c/Users/jrgm")
        values.put("TEMP","/c/temp")
        values.put("PATCH","/c/CA/Patchs")
    }


    fun getOption(optionNumber: Int, filePath:String ): String {

        test()

        val option:Int = optionNumber
        val endOption = option.plus(1)

        val lines = File(filePath).readLines()
        val drop = lines.size.minus(lines.indexOf("--$endOption--"))
        val stringVal = lines.dropLast(drop).drop(lines.indexOf("--$option--").plus(1)).joinToString(separator = " && ")

        println("command: $stringVal")

        val sub = StringSubstitutor(values)
        return sub.replace(stringVal)
    }


    fun test(){
        val str = """--2--
cd {PW}
docker-compose up -d
--3--
cd {PW}
docker-compose down
--4.1--
cd {PW}/db-migration
gradle doMigration > {TEMP}/salida_migration.txt
cd {TEMP}
kotlinc -script validaMigrationv2.kts
--5--
cd {PW}
gradle clean server:bootRun
--6--
cd {PW}
gradle clean server:build -x :client:build
--7--"""

        //get the menu numbers
        val regex ="""--([\d.]+)--""".toRegex(RegexOption.MULTILINE)
        val regex2 ="""([\d.]+)""".toRegex(RegexOption.MULTILINE)
        val menuNumbers = regex.findAll(str).map { regex2.find(it.value)}.map { it?.value }.toList()
        println(menuNumbers.toString())

        //get the menu items
        val separate1 = str.split("""--([\d.]+)--""".toRegex()).filter { it.isNotEmpty() }
        println(separate1)




    }

}