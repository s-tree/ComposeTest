package com.jingxi.library.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Hashtable

object BitmapUtil {

    fun createQR(data: String,width: Int,margin: Int):Bitmap?{
        if(width <= 0 || data.isEmpty()){
            return null
        }
        try {
            val hints: Hashtable<EncodeHintType, Any> = Hashtable<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            if (margin >= 0) {
                hints[EncodeHintType.MARGIN] = margin
            }
            val bitMatrix: BitMatrix =
                QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, width, width, hints)
            val pixels = IntArray(width * width)
            for (y in 0 until width) {
                for (x in 0 until width) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = -0x1000000
                    } else {
                        pixels[y * width + x] = 0x00000000
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, width)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}