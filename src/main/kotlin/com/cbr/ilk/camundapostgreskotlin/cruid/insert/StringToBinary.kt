package com.cbr.ilk.camundapostgreskotlin.cruid.insert

import java.nio.charset.StandardCharsets


object StringToBinary {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "a"
        val result = convertByteArraysToBinary(input.toByteArray(StandardCharsets.UTF_8))
//        println(prettyBinary(result, 8, " "))
        println(result)

    }

    fun convertByteArraysToBinary(input: ByteArray): String {
        val result = StringBuilder()
        for (b in input) {
            var `val` = b.toInt()
            for (i in 0..7) {
                result.append(if (`val` and 128 == 0) 0 else 1) // 128 = 1000 0000
                `val` = `val` shl 1
            }
        }
        return result.toString()
    }

    fun prettyBinary(binary: String?, blockSize: Int, separator: String?): String {
        //... same with 1.1
        return ""
    }
}