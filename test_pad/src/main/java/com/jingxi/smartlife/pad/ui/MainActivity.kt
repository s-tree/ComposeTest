package com.jingxi.smartlife.pad.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jingxi.smartlife.pad.Main
import com.jingxi.smartlife.pad.ui.constant.Page

class MainActivity(): ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor = Color.Transparent.toArgb()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContent {
            MaterialTheme {
                Surface(color = Color.White) {
                    NavHostMain()
                }
            }
        }
    }

    @Composable
    fun NavHostMain(){
        val navControl = rememberNavController()
        NavHost(navController = navControl, startDestination = Page.MAIN){

            composable(Page.MAIN){
                Main().ui()
            }

        }
    }
}