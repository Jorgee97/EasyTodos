package com.coreteam.easytodos.util

class StringGenerator {

    fun AlphanumericGenerator() : String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..12)
            .map { source.random() }
            .joinToString("")
    }
}