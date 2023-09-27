package com.jingxi.library.weiget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefreshLayout(loadingState: State<Boolean>, onRefresh: () -> Unit, content: @Composable BoxScope.() -> Unit){
    val pullRefreshState = rememberPullRefreshState(
        refreshing = loadingState.value,
        onRefresh = onRefresh)
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pullRefresh(pullRefreshState)) {
        content()
        PullRefreshIndicator(refreshing = loadingState.value, state = pullRefreshState)
    }
}