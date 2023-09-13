package com.jingxi.test_xiaorun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jingxi.test_xiaorun.constant.Page
import com.jingxi.test_xiaorun.ui.login.Login
import com.jingxi.test_xiaorun.ui.login.LoginHome

class XiaoRunActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = Color(red = 0xff, green = 0xff,blue = 0xff)) {
                    NavHostMain()
                }
            }
        }
    }

    @Composable
    fun NavHostMain(){
        val navControl = rememberNavController();
        NavHost(navController = navControl, startDestination = Page.LOGIN){

            composable(Page.LOGIN){
                Login().Main(navControl)
            }

            composable(Page.HOME){

            }
        }
    }

}