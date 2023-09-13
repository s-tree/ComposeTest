package com.jingxi.test_xiaorun.filter

fun LengthFilter(str : String,max : Int):String {
    if(str.length <= max){
        return str
    }
    else{
        return str.substring(0,max)
    }
}