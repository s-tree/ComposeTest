package com.jingxi.test_xiaorun.util

import android.widget.Toast
import com.jingxi.library.BaseApplication
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ToastUtil {

    companion object{
        private val toast : Toast = Toast.makeText(BaseApplication.instance,"",Toast.LENGTH_SHORT)

        fun show(text : String){
            MainScope().launch {
                if(text.isNotEmpty()){
                    toast.setText(text)
                    toast.show()
                }
            }
        }
    }
}