package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jingxi.library.weiget.PullRefreshAnimLayout

class PullRefreshLayoutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val refreshingState = remember {
                mutableStateOf(false)
            }
            val data = MutableList(31) { 0 }
            for (i in 0..30) {
                data[i] = i
            }

            PullRefreshAnimLayout(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                refreshing = refreshingState.value,
                onRefresh = {
                    /**
                     * 保持在加载状态
                     */
                    refreshingState.value = true
                },
                items = data
            ) { index, item ->
                Text(
                    text = "$item",
                    fontSize = 26.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}