package com.jingxi.test_xiaorun.ui.login

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Login {

    @Composable
    fun Main(activityController: NavController){
        LoginNavHost(activityController)
    }

    @Composable
    fun LoginNavHost(activityController: NavController){
        val loginController = rememberNavController()
        NavHost(navController = loginController, startDestination = LoginPage.HOME,
        Modifier.background(color = Color.White)){

            composable(LoginPage.HOME){
                LoginHome(navController = loginController)
            }

            composable(LoginPage.REGISTER){
                LoginRegister(loginController)
            }

            composable(LoginPage.LOGIN){
                LoginLogin(loginController,activityController)
            }
        }
    }
}