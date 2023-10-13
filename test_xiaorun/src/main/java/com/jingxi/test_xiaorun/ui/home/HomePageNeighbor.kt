package com.jingxi.test_xiaorun.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jingxi.library.paging.BasePagingSource
import com.jingxi.library.paging.PagingPager
import com.jingxi.library.paging.defaultLoadMoreViews
import com.jingxi.library.paging.pagingStatus
import com.jingxi.library.weiget.SwipeRefreshLayout
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.ui.weiget.statusBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageNeighbor(activityNavController: NavController) {
    statusBar(color = colorResource(id = R.color.white), isDarkIcon = true, fitSystemWindow = true)

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        neighborTitleBar()

        val tabs = listOf("全部", "闲置", "活动")
        val pageItems = remember {
            mutableListOf<LazyPagingItems<NeighborBean>>()
        }
        if(pageItems.isEmpty()){
            tabs.forEach { tabName ->
                pageItems.add(
                    PagingPager(
                        pageSize = 10,
                        pagerSource = NeighborSource(typeName = tabName)
                    ).collectAsLazyPagingItems()
                )
            }
        }

        val pageState = rememberPagerState(0)
        ScrollableTabRow(
            selectedTabIndex = pageState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White),
            edgePadding = 10.dp,
            contentColor = Color.Transparent,
            containerColor = Color.Transparent,
            indicator = {tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pageState.currentPage]),
                    color = colorResource(id = R.color.colorAccentBlue)
                )
            },
            divider = {
                Spacer(
                    Modifier
                        .size(0.dp)
                        .background(Color.Transparent))
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelect = index == pageState.currentPage
                Tab(
                    text = {
                        Text(text = title,
                            fontSize = if(isSelect) 18.sp else 14.sp,
                            color = if(isSelect) colorResource(id = R.color.color_ff323232) else colorResource(id = R.color.tv_gray_848484))
                    },
                    selected = index == pageState.currentPage,
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            pageState.scrollToPage(index, 0f)
                        }
                    })
            }
        }

        val refreshingState = remember {
            mutableStateOf(false)
        }

        HorizontalPager(
            state = pageState, pageCount = tabs.size, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) { page ->
            val pageItem = pageItems[page]
            SwipeRefreshLayout(
                loadingState = refreshingState,
                onRefresh = {
                    pageItem.refresh()
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    pageItem.apply {
                        items(items = pageItem) { item ->

                            Column(Modifier.fillMaxWidth()) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(27.dp)
                                        .background(colorResource(id = R.color.bg_color_gary_f3f2f5))
                                )
                                item?.let { NeighborItem(it) }
                            }
                        }

                        item {
                            defaultLoadMoreViews(pageItem)
                        }
                    }
                }
            }

            /**
             * refresh 事件需要放到外部来监听
             */
            pagingStatus(
                pagingItems = pageItem,
                onRefresh = { refreshing -> run { refreshingState.value = refreshing } })
        }

    }
}

@Composable
fun neighborTitleBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(88.dp)
    ) {
        Text(
            text = "邻里圈",
            fontSize = 36.sp,
            color = colorResource(id = R.color.color_ff323232),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 31.dp)
        )

        Image(
            painter = painterResource(id = R.mipmap.lib_default_head_img),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(60.dp)
                .clip(shape = RoundedCornerShape(60.dp))
        )

        Image(
            painter = painterResource(id = R.mipmap.infomation_icon), contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp)
                .padding(end = 16.dp)
        )
    }
}


@Composable
fun NeighborItem(neighborBean: NeighborBean) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 34.dp, start = 29.dp, end = 29.dp, bottom = 27.dp)
    ) {
        val (headRes, nameRes, timeRes, moreRes, contentRes, picRes, bodyRes) = createRefs()

        Image(
            painter = painterResource(id = neighborBean.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .constrainAs(headRes) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                })

        Text(text = neighborBean.nickName,
            color = colorResource(id = R.color.color_ff323232),
            fontSize = 32.sp,
            modifier = Modifier.constrainAs(nameRes) {
                start.linkTo(headRes.end, margin = 26.dp)
                top.linkTo(headRes.top, margin = 2.dp)
            })

        Text(text = neighborBean.timeStr,
            color = colorResource(id = R.color.tv_gray_646464),
            fontSize = 22.sp,
            modifier = Modifier.constrainAs(timeRes) {
                start.linkTo(nameRes.start)
                bottom.linkTo(headRes.bottom, margin = 2.dp)
            })

        if (neighborBean.nickName == "张三") {
            Image(painter = painterResource(id = R.mipmap.icon_more_point),
                contentDescription = null,
                Modifier
                    .width(78.dp)
                    .height(48.dp)
                    .constrainAs(moreRes) {
                        end.linkTo(parent.end, margin = 27.dp)
                    })
        }

        if (neighborBean.content.isNotEmpty()) {
            Text(text = neighborBean.content,
                color = colorResource(id = R.color.color_ff323232),
                fontSize = 32.sp,
                lineHeight = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(contentRes) {
                        top.linkTo(headRes.bottom, margin = 32.dp)
                    })
        }

        val contentBaseLine = createBottomBarrier(headRes, contentRes)

        if (neighborBean.imageList.isNotEmpty()) {
            if (neighborBean.imageList.size == 1) {
                Image(painter = painterResource(id = neighborBean.imageList[0]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .constrainAs(picRes) {
                            top.linkTo(contentBaseLine, margin = 23.dp)
                        })
            } else {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(picRes) {
                        top.linkTo(contentBaseLine, margin = 23.dp)
                    }) {
                    val imageLength = neighborBean.imageList.size
                    for (i in 0 until imageLength step 3) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (k in 0..2) {
                                val index = i * 3 + k
                                val modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .weight(1f)
                                if (index < imageLength) {
                                    Image(
                                        painter = painterResource(id = neighborBean.imageList[index]),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = modifier
                                    )
                                    if(k != 2){
                                        Spacer(modifier = Modifier.width(20.dp))
                                    }
                                } else {
                                    Spacer(modifier = modifier)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

open class NeighborSource(val typeName: String) : BasePagingSource<NeighborBean>() {

    override fun newObject(): BasePagingSource<NeighborBean> {
        return NeighborSource(typeName)
    }

    override suspend fun loadData(pageIndex: Int): List<NeighborBean> {
        delay(2000)
        val data = mutableListOf<NeighborBean>()
        if (pageIndex == 1) {
            data.add(
                NeighborBean(
                    imageRes = R.mipmap.lib_default_head_img,
                    nickName = "张三",
                    content = "大幅拉低解放路卡江东父老咖啡机",
                    timeStr = "2023-10-07 15:18"
                )
            )
            data.add(
                NeighborBean(
                    imageRes = R.mipmap.lib_default_head_img,
                    nickName = "李四",
                    content = "大幅拉低解放路卡江东父答复啊打发打发老咖啡机",
                    imageList = listOf(R.mipmap.icon_rect_temp),
                    timeStr = "2023-10-07 15:18"
                )
            )
            data.add(
                NeighborBean(
                    imageRes = R.mipmap.lib_default_head_img,
                    nickName = "王五",
                    content = "大幅拉低解放路卡江东父答复啊发打发斯蒂芬打发打发老咖啡机",
                    imageList = listOf(R.mipmap.icon_rect_temp, R.mipmap.icon_rect_temp),
                    timeStr = "2023-10-07 15:18",
                    type = "seller",
                    price = 15.60f,
                    currentPrice = 10.00f
                )
            )
            data.add(
                NeighborBean(
                    imageRes = R.mipmap.lib_default_head_img,
                    nickName = "王五",
                    content = "大幅拉低解放路卡江东父答复啊发打发斯蒂芬打发打发老咖啡机",
                    imageList = listOf(R.mipmap.icon_rect_temp, R.mipmap.icon_rect_temp),
                    timeStr = "2023-10-07 15:18",
                    type = "activity",
                    activityStatus = 0,
                    activityJoined = 10,
                    activityEndLine = System.currentTimeMillis()
                )
            )
            data.add(
                NeighborBean(
                    imageRes = R.mipmap.lib_default_head_img,
                    nickName = "王五",
                    content = "大幅拉低解放路卡江东父答复啊发打发斯蒂芬打发打发老咖啡机",
                    imageList = listOf(R.mipmap.icon_rect_temp, R.mipmap.icon_rect_temp,R.mipmap.icon_rect_temp),
                    timeStr = "2023-10-07 15:18",
                    type = "activity",
                    activityStatus = 1,
                    activityJoined = 10,
                    activityEndLine = System.currentTimeMillis() + 3600 * 12
                )
            )
        }
        return data
    }
}

data class NeighborBean(
    val imageRes: Int,
    val nickName: String,
    val content: String,
    val timeStr: String,
    val imageList: List<Int> = listOf(),
    val type: String = "message",
    val price: Float = 0.0f,
    val currentPrice: Float = 0.0f,
    val activityStatus: Int = 0,
    val activityJoined: Int = 0,
    val activityEndLine: Long = 0
)