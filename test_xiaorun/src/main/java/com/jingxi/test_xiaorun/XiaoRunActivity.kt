package com.jingxi.test_xiaorun

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jingxi.test_xiaorun.constant.Page
import com.jingxi.test_xiaorun.ui.login.Login
import com.jingxi.test_xiaorun.ui.web.WebViewMain
import com.jingxi.test_xiaorun.ui.web.WebViewParams
import com.jingxi.test_xiaorun.util.HtmlFileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.jessyan.autosize.AutoSizeCompat

class XiaoRunActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.WHITE
        setContent {
            MaterialTheme {
                Surface(color = Color(red = 0xff, green = 0xff,blue = 0xff)) {
                    NavHostMain()
                }
            }
        }

        CoroutineScope(Dispatchers.IO)
            .launch { HtmlFileUtils.start() }
    }

    @Composable
    fun NavHostMain(){
        val navControl = rememberNavController()
//        navControl.enableOnBackPressed(true)
//        onBackPressedDispatcher.addCallback{
//            if(!navControl.popBackStack()){
//                finish()
//            }
//        }
//        navControl.setLifecycleOwner(this)
//        navControl.setOnBackPressedDispatcher(onBackPressedDispatcher)
        NavHost(navController = navControl, startDestination = Page.LOGIN){

            composable(Page.LOGIN){
                Login().Main(navControl)
            }

            composable(Page.HOME){

            }

            composable("${Page.WEB_VIEW}?${WebViewParams.URL}={${WebViewParams.URL}}"){
                val argument = requireNotNull(it.arguments)
                val url = checkNotNull(argument.getString(WebViewParams.URL)){""}
                WebViewMain(navController = navControl, url = url)
            }
        }
    }

    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }
}