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

        fun noEmptyFilter(string: String):String{
            return string.replace(Regex("\\s+"),"")
        }

        fun abcFilter(string: String):String{
            return string.replace(Regex("[^a-zA-Z]"),"")
        }

        fun passwordFilter(string: String):String{
            return string.replace(Regex("[^a-zA-Z0-9！@#￥%&_=]"),"")
        }

        fun noEmojiFilter(string: String):String{
            return string.replace(Regex("[^a-zA-Z0-9\u4E00-\u9FA5]"),"")
        }

        fun chineseFilter(string: String):String{
            return string.replace(Regex("[^\u4E00-\u9FA5]"),"")
        }

        fun toPassword(string:String):String{
            return string.replace(Regex("\\w"),"*")
        }
    }
}