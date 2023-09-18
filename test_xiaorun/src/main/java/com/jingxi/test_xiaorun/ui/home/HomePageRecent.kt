package com.jingxi.test_xiaorun.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomePageRecent(activityNavController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
            contentAlignment = Alignment.Center){
        
        Text(text = "这个是会话页面")
    }
}