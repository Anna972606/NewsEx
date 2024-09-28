package com.example.newsex

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object TestUtils {
    fun getJsonFromResource(name: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$name")
        val stringBuilder = StringBuilder()
        inputStream?.let {
            try {
                val streamReader = InputStreamReader(it)
                val source = BufferedReader(streamReader)
                for (line in source.readLines()) {
                    stringBuilder.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return stringBuilder.toString()
    }
}