package com.jingxi.test_xiaorun.ui.weiget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun statusBar(color: Color = Color.Transparent,isDarkIcon: Boolean,fitSystemWindow : Boolean) : Modifier{
    rememberSystemUiController().run {
        // 设置状态栏颜色
        setStatusBarColor(
            color = color,
            darkIcons = isDarkIcon
        )
        // 将状态栏和导航栏设置为color
        setSystemBarsColor(color = color, darkIcons = isDarkIcon)
        // 设置导航栏颜色
        setNavigationBarColor(color = color, darkIcons = isDarkIcon)
    }

    return if(fitSystemWindow){
        Modifier.statusBarsPadding()
    }else{
        Modifier
    }
}

@Composable
fun statusBar2(color: Color = Color.Transparent,isDarkIcon: Boolean,fitSystemWindow : Boolean,content: @Composable () -> Unit){
    ProvideWindowInsets {
        rememberSystemUiController().run {
            // 设置状态栏颜色
            setStatusBarColor(
                color = color,
                darkIcons = isDarkIcon
            )
            // 将状态栏和导航栏设置为color
            setSystemBarsColor(color = color, darkIcons = isDarkIcon)
            // 设置导航栏颜色
            setNavigationBarColor(color = color, darkIcons = isDarkIcon)
        }
        Column(
            Modifier
                .fillMaxSize()) {

            if(fitSystemWindow){
                Spacer(modifier = Modifier.statusBarsPadding().fillMaxWidth())
            }

            content()
        }
    }
}