package com.desco.aoc2015

import com.desco.Day
import java.math.BigInteger
import java.security.MessageDigest

object Day04: Day(4, 2015) {

    override fun partOne(): Any {
        var i = 0
        while (true) {
            if ("$inputString$i".toMD5().startsWith("00000")) {
                return i
            }
            i++
        }
    }

    override fun partTwo(): Any {
        var i = 0
        while (true) {
            if ("$inputString$i".toMD5().startsWith("000000")) {
                return i
            }
            i++
        }    }

    private fun String.toMD5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }
}