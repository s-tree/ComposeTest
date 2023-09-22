package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PullRefreshActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            swipeRefreshLayout()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun swipeRefreshLayout(){
        val refreshingState = remember {
            mutableStateOf(false)
        }

        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshingState.value,
            onRefresh = {
                refreshingState.value = true
                print("in refresh")
            })
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .pullRefresh(pullRefreshState)) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()){
                items(count = 30){
                    Text(text = "$it",
                        fontSize = 26.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        textAlign = TextAlign.Center)
                }

            }
            PullRefreshIndicator(refreshing = refreshingState.value, state = pullRefreshState)
        }
    }
}