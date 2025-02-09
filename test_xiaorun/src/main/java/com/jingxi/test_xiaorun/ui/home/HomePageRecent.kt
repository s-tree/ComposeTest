package com.jingxi.test_xiaorun.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jingxi.library.paging.BasePagingSource
import com.jingxi.library.paging.defaultLoadMoreViews
import com.jingxi.library.paging.pagingStatus
import com.jingxi.library.weiget.PullRefreshAnimLayout
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.ui.home.viewmodel.RecentBean
import com.jingxi.test_xiaorun.ui.home.viewmodel.RecentViewModel
import com.jingxi.test_xiaorun.ui.weiget.statusBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomePageRecent(activityNavController: NavController){
    statusBar(color = colorResource(id = R.color.bg_color_gary_f3f2f5), isDarkIcon = true, fitSystemWindow = true)
    val recentViewModel = viewModel(modelClass = RecentViewModel::class.java)
    var pageI : LazyPagingItems<RecentBean> ?= recentViewModel.pageData
    if(pageI == null){
        pageI = recentViewModel.pageItemFlow.collectAsLazyPagingItems()
        recentViewModel.pageData = pageI
    }

    val pageItem : LazyPagingItems<RecentBean> = recentViewModel.pageItemFlow.collectAsLazyPagingItems()

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.bg_color_gary_f3f2f5))
    ) {
        recentTitleBar()
        val refreshingState = remember {
            mutableStateOf(false)
        }

        PullRefreshAnimLayout(
            modifier = Modifier
                .padding(start = 45.dp, end = 45.dp, bottom = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            refreshing = refreshingState,
            onRefresh = {
//                recentViewModel.viewModelScope.launch {
//                    withContext(Dispatchers.IO){
//                        delay(2000)
//                    }
                    pageI.refresh()
//                }
            }
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                pageI.apply {
                    items(items = pageI){
                        value ->
                        value?.let { recentItem(recentBean = it) }
                    }

                    item{
                        defaultLoadMoreViews(pageI)
                    }
                }
            }
        }


        /**
         * refresh 事件需要放到外部来监听
         */
        pagingStatus(
            pagingItems = pageI,
            onRefresh = {refreshing -> run { refreshingState.value = refreshing }})
    }
}

@Composable
fun recentTitleBar(){
    /**
     * titleBar 不设置背景 会导致下拉刷新组件 显示出界
     */
    Text(text = "消息",
        fontSize = 36.sp,
        color = colorResource(id = R.color.color_ff323232),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(color = colorResource(id = R.color.bg_color_gary_f3f2f5))
            .zIndex(2f))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun recentItem(recentBean: RecentBean){
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))) {

        val (iconRes,titleRes,subTitleRes,timeRes,toDetailRes,moreIconRes) = remember {
            createRefs()
        }

        Image(
            painter = painterResource(id = recentBean.iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .constrainAs(iconRes) {
                    start.linkTo(parent.start, margin = 30.dp)
                    top.linkTo(parent.top, margin = 40.dp)
                })

        Text(
            text = recentBean.title,
            fontSize = 30.sp,
            color = colorResource(id = R.color.color_ff323232),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(titleRes){
                start.linkTo(iconRes.end, margin = 20.dp)
                top.linkTo(iconRes.top)
                bottom.linkTo(iconRes.bottom)
            })

        Text(
            text = recentBean.time,
            fontSize = 24.sp,
            color = colorResource(id = R.color.color_ff323232),
            maxLines = 1,
            modifier = Modifier.constrainAs(timeRes){
                top.linkTo(iconRes.top)
                bottom.linkTo(iconRes.bottom)
                end.linkTo(parent.end, margin = 32.dp)
            })

        Text(
            text = recentBean.subTitle,
            fontSize = 26.sp,
            color = colorResource(id = R.color.tv_gray_848484),
            modifier = Modifier
                .padding(end = 32.dp)
                .constrainAs(subTitleRes) {
                    top.linkTo(iconRes.bottom, margin = 25.dp)
                    start.linkTo(iconRes.start)
                }
        )

        Text(
            text = "查看详情",
            fontSize = 26.sp,
            color = colorResource(id = R.color.tv_gray_848484),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .constrainAs(toDetailRes) {
                    start.linkTo(iconRes.start)
                    top.linkTo(subTitleRes.bottom, margin = 25.dp)
                })

        Image(
            painter = painterResource(id = R.mipmap.icon_to_end_gray),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .constrainAs(moreIconRes) {
                    top.linkTo(toDetailRes.top)
                    bottom.linkTo(toDetailRes.bottom)
                    end.linkTo(parent.end, margin = 32.dp)
                })
    }
    
    Spacer(modifier = Modifier.height(20.dp))
}