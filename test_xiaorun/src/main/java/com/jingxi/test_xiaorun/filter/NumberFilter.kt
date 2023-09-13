package com.jingxi.test_xiaorun.filter

fun NumberFilter(string: String):String {
    return string.filter { it.isDigit() }
}