package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jingxi.composetest.composeui.firstView

class FirstActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { firstView(click = {backClick()}) }
    }

    private fun backClick(){
        finish()
    }
}