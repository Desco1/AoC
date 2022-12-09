package com.desco

import java.io.File
import java.io.FileWriter
import java.net.HttpURLConnection
import java.net.URL

object InputReader {

    fun getInputAsString(day: Int, year: Int): String {
        return getInput(day, year).readText().replace("\n", "")
    }

    fun getInputAsList(day: Int, year: Int): List<String> {
        return getInput(day, year).readLines()
    }

    private fun getCookie(): String {
        return File(javaClass.classLoader.getResource("session.txt").toURI()).readText()
    }

    private fun getInput(day: Int, year: Int): File {
        val puzzleFile = File("./puzzleinputs/$year/$day.txt")
        if (!puzzleFile.exists()) {
            puzzleFile.parentFile.mkdirs()
            puzzleFile.createNewFile()
            val conn = URL("https://adventofcode.com/$year/day/$day/input").openConnection() as HttpURLConnection
            conn.doInput = true
            conn.doOutput = false
            conn.setRequestProperty("Cookie", "session=${getCookie()}")
            conn.inputStream.use { stream ->
                FileWriter(puzzleFile).use {
                    it.write(stream.readBytes().toString(Charsets.UTF_8))
                }
            }
        }
        return puzzleFile
    }
}