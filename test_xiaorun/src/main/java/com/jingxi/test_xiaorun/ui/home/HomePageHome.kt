package com.jingxi.test_xiaorun.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.bean.MainConfBean
import com.jingxi.test_xiaorun.ui.weiget.AutoFitGridLayout
import com.jingxi.test_xiaorun.ui.weiget.PagerCircleIndicator
import com.jingxi.test_xiaorun.ui.weiget.statusBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageHome(activityNavController: NavController) {
    statusBar(Color.White, isDarkIcon = true, fitSystemWindow = true)

    val scrollState = remember {
        ScrollState(0)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.color_fff2f2f2))
    ) {
        titleBar(activityNavController)


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White)
                .zIndex(2f)
                .verticalScroll(state = scrollState)
        ) {

            weatherBar(activityNavController = activityNavController)

            adBar(activityNavController)

            modulesBar(activityNavController)

            noticeBar(activityNavController)
        }
    }
}

@Composable
fun titleBar(activityNavController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(88.dp)
            .padding(start = 27.dp, end = 27.dp)
            .zIndex(2f)
    ) {
        val (icon, communityName, qrIcon) = createRefs()

        Image(
            painter = painterResource(id = R.mipmap.icon_location_blue),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "社区名称",
            fontSize = 36.sp,
            color = colorResource(id = R.color.color_ff323232),
            modifier = Modifier
                .constrainAs(communityName) {
                    start.linkTo(icon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 6.dp),
            maxLines = 1)

        Image(painter = painterResource(id = R.mipmap.icon_qr), contentDescription = null,
            modifier = Modifier.constrainAs(qrIcon) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
    }
}

@Composable
fun weatherBar(activityNavController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 27.dp, end = 38.dp, top = 5.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "天气环境",
            color = colorResource(id = R.color.green_34b185),
            fontSize = 20.sp,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.green_38cc98),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(start = 12.dp, end = 12.dp, top = 9.dp, bottom = 9.dp)
        )

        Text(
            text = "今天",
            Modifier.padding(start = 14.dp),
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "16℃",
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "|",
            modifier = Modifier.padding(start = 20.dp),
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "湿度",
            modifier = Modifier.padding(start = 20.dp),
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "10%",
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "|",
            modifier = Modifier.padding(start = 20.dp),
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "PM2.5",
            modifier = Modifier.padding(start = 20.dp),
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )

        Text(
            text = "500",
            modifier = Modifier.padding(start = 20.dp),
            color = colorResource(id = R.color.color_bf323232),
            fontSize = 26.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun adBar(activityNavController: NavController) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 27.dp, end = 38.dp, bottom = 20.dp)
    ) {
        val loginPics = listOf(R.mipmap.login_page_1, R.mipmap.login_page_2, R.mipmap.login_page_3)
        val state = rememberPagerState(0);
        HorizontalPager(
            loginPics.size,
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Image(
                painter = painterResource(loginPics[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(27.dp))

        PagerCircleIndicator(
            state = state,
            count = loginPics.size,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun modulesBar(activityNavController: NavController) {
    val modules = listOf(
        MainConfBean(R.mipmap.icon_discount, "模块一"),
        MainConfBean(R.mipmap.icon_family_points, "模块二"),
        MainConfBean(R.mipmap.icon_health, "模块三"),
        MainConfBean(R.mipmap.icon_wait_comment, "模块四"),
        MainConfBean(R.mipmap.icon_motion, "模块五"),
        MainConfBean(R.mipmap.icon_my_order, "模块六"),
        MainConfBean(R.mipmap.icon_noman_shop, "模块七"),
        MainConfBean(R.mipmap.icons_yuyue_log, "模块八"),
        MainConfBean(R.mipmap.icon_wait_confirm, "模块九"),
        MainConfBean(R.mipmap.icon_real_name, "模块十"),
        MainConfBean(R.mipmap.icon_wait_deliver_goods, "模块一"),
        MainConfBean(R.mipmap.icon_wait_pay, "模块一"),
        MainConfBean(R.mipmap.icon_wait_receive_order, "模块一"),
        MainConfBean(R.mipmap.icon_wallet, "模块一"),
        MainConfBean(R.mipmap.icons_delivery, "模块一"),
        MainConfBean(R.mipmap.icons_health_log, "模块一"),
        MainConfBean(R.mipmap.icons_yuyue_log, "模块一"),
    )
    val rowCount = 5
    val columnCount = 2
    val pageCount =
        modules.size / (rowCount * columnCount) + if (modules.size % (rowCount * columnCount) == 0) {
            0
        } else {
            1
        }
    val state = rememberPagerState(0);
    HorizontalPager(
        pageCount,
        state = state,
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) { page ->
        val start = page * rowCount * columnCount
        val end = modules.size.coerceAtMost(start + rowCount * columnCount)
        AutoFitGridLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), data = modules.subList(start, end)
        ) { mainConfBean ->
            Column(
                Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = mainConfBean.resourceId),
                    contentDescription = null,
                    Modifier
                        .padding(bottom = 18.dp)
                        .size(64.dp)
                )

                Text(text = mainConfBean.confName,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.color_ff323232),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            }
        }
    }

    PagerCircleIndicator(state = state, count = pageCount, modifier = Modifier.padding(bottom = 25.dp).fillMaxWidth())
}

@Composable
fun noticeBar(activityNavController: NavController) {
    Row(
        Modifier
            .padding(start = 27.dp, end = 38.dp, bottom = 52.dp)
            .fillMaxWidth()
            .height(120.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(id = R.color.bg_blue_deep_start),
                        colorResource(id = R.color.bg_blue_deep_end)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.mipmap.icon_notification_white),
            contentDescription = null,
            Modifier
                .padding(start = 26.dp, end = 28.dp)
                .size(60.dp),
        )

        Text(
            text = "物业通知:张三李四王五赵六张三李四王五赵六张三李四王五赵六张三李四王五赵六张三李四王五赵六张三李四王五赵六",
            fontSize = 26.sp,
            color = Color.White,
            maxLines = 2,
            lineHeight = 30.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 45.dp)
        )
    }
}