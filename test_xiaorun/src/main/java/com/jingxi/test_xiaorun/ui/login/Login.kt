package com.jingxi.test_xiaorun.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.ui.web.WebViewParams
import com.jingxi.test_xiaorun.util.HtmlFileFactory

class Login {

    @Composable
    fun Main(activityController: NavController){
        LoginNavHost(activityController)
    }

    @Composable
    fun LoginNavHost(activityController: NavController){
        val loginController = rememberNavController()
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
            contentAlignment = Alignment.BottomCenter) {

            val checkNote = remember {
                mutableStateOf(false)
            }

            val currentPage = remember {
                mutableStateOf(LoginPage.HOME)
            }

            NavHost(navController = loginController, startDestination = LoginPage.HOME){

                composable(LoginPage.HOME){
                    LocalFocusManager.current.clearFocus(true)
                    currentPage.value = LoginPage.HOME
                    LoginHome(navController = loginController)
                }

                composable(LoginPage.REGISTER){
                    LocalFocusManager.current.clearFocus(true)
                    currentPage.value = LoginPage.REGISTER
                    LoginRegister(loginController)
                }

                composable(LoginPage.LOGIN){
                    LocalFocusManager.current.clearFocus(true)
                    currentPage.value = LoginPage.LOGIN
                    LoginLogin(loginController,activityController)
                }

                composable(LoginPage.FORGET){
                    LocalFocusManager.current.clearFocus(true)
                }
            }

            if(currentPage.value == LoginPage.REGISTER
                || currentPage.value == LoginPage.LOGIN) {
                Row(Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    Image(painter = painterResource(
                        id = if (checkNote.value) { R.mipmap.icon_circle_slected } else {R.drawable.baseline_brightness_1_24}
                    ), contentDescription = "",
                        Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .clickable(onClick = {checkNote.value = !checkNote.value}))
                    
                    Text(text = "登录注册代表你已同意",
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.tv_gray),
                        modifier = Modifier.padding(start = 18.dp))

                    Text(text = "《用户协议》",
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.tv_gray),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(start = 18.dp)
                            .clickable ( onClick = {
                                activityController.navigate(WebViewParams.buildUrl(HtmlFileFactory.buildUrl(HtmlFileFactory.AGREEMENT_USER)))
                            } ))

                    Text(text = "和",
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.tv_gray),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(start = 18.dp))

                    Text(text = "《隐私协议》",
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.tv_gray),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(start = 18.dp)
                            .clickable {
                                activityController.navigate(WebViewParams.buildUrl(HtmlFileFactory.buildUrl(HtmlFileFactory.AGREEMENT_PRIVACY)))
                            })
                }
            }
        }
    }
}