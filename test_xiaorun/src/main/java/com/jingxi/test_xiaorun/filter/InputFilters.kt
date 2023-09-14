package com.jingxi.test_xiaorun.filter

class InputFilters {

    companion object{

        fun lengthFilter(str : String,max : Int):String {
            return if(str.length <= max){
                str
            } else{
                str.substring(0,max)
            }
        }

        fun numberFilter(string: String):String {
            return string.filter { it.isDigit() }
        }
    }
}