package com.jingxi.test_xiaorun.util

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

internal object FileUtil {

    fun readFile(file : File):String{
        return try{
            val input = FileInputStream(file)
            val output = ByteArrayOutputStream()
            val bytes = ByteArray(1024 * 10)
            var index = -1;
            while (input.read(bytes).also { index = it } != -1){
                output.write(bytes,0,index)
            }
            input.close()
            val result = output.toString()
            output.close()
            result
        }catch (e : Exception){
            ""
        }
    }

    fun writeFile(file: File,data:String){
        try{
            if(file.exists()){
                file.delete()
            }
            file.createNewFile()
            val output = FileOutputStream(file)
            output.write(data.toByteArray())
            output.flush()
            output.close()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}