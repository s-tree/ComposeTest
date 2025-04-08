package com.jingxi.library.util

import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object FileUtil {
    fun readFile(file: File?): String {
        if (file == null || !file.exists()) {
            return ""
        }
        val stringBuilder = StringBuilder()
        var inputStream: InputStream? = null
        try {
            inputStream = FileInputStream(file)
            val bytes = ByteArray(1024)
            var index = -1
            while (inputStream.read(bytes).also { index = it } != -1) {
                stringBuilder.append(String(bytes, 0, index))
            }
        } catch (e: java.lang.Exception) {
        } finally {
            inputStream?.close()
        }
        return stringBuilder.toString()
    }

    fun readInt(filePath: String): Int{
        return try {
            val carrier: String = readFile(File(filePath))
            carrier.trim { it <= ' ' }.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}