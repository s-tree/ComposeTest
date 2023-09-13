package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jingxi.composetest.navigation.ParamsConfig
import com.jingxi.composetest.navigation.RouteConfig

class NavigationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            MaterialTheme{
                Surface(color = Color(red = 0xff, green = 0xff,blue = 0xff)) {
                    NavHostDemo()
                }
            }
        }
    }

    @Composable
    fun PageOne(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White
                )
        ) {
            Text(text = "这是页面1")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                val name = "张三"
                val age = 33
                navController.navigate("${RouteConfig.ROUTE_PAGETWO}/$name?${ParamsConfig.PARAMS_AGE}=$age")
            }) {
                Text(
                    text = "跳转页面2",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun PageTwo(navController: NavController,name : String?,age : Int) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White
                )
        ) {
            Text(text = "这是页面2")
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "我是 $name 我今年 $age 岁了")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                navController.navigate(RouteConfig.ROUTE_PAGEONE)
            }) {
                Text(
                    text = "返回页面1",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun NavHostDemo(){
        val navControl = rememberNavController();
        NavHost(navController = navControl, startDestination = RouteConfig.ROUTE_PAGEONE){

            composable(RouteConfig.ROUTE_PAGEONE){
                PageOne(navControl)
            }

            composable(
                "${RouteConfig.ROUTE_PAGETWO}/{${ParamsConfig.PARAMS_NAME}}" + "?${ParamsConfig.PARAMS_AGE}={${ParamsConfig.PARAMS_AGE}}",
                    arguments = listOf(
                        navArgument(ParamsConfig.PARAMS_NAME){},
                        navArgument(ParamsConfig.PARAMS_AGE){
                            defaultValue = 30
                            type = NavType.IntType
                        }
                    )
            ){
                val argument = requireNotNull(it.arguments)
                val name = argument.getString(ParamsConfig.PARAMS_NAME)
                val age = argument.getInt(ParamsConfig.PARAMS_AGE)
                PageTwo(navControl,name,age)
            }
        }
    }
}