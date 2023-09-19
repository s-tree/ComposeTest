package com.jingxi.test_xiaorun.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.ui.weiget.PagerCircleIndicator
import com.jingxi.test_xiaorun.ui.weiget.statusBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginHome(navController: NavController){
    val modifier = statusBar(Color.White, isDarkIcon = true, fitSystemWindow = true)

    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color.White)) {
        val (pageRes,circleIndicatorRes,spaceRes,registerRes,loginRes) = remember {
            createRefs()
        }

        val loginPics = listOf(R.mipmap.login_page_1,R.mipmap.login_page_2,R.mipmap.login_page_3)
        val state = rememberPagerState(0);
        HorizontalPager(loginPics.size,
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 0.75f)
                .constrainAs(pageRes) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){
                page ->
            Image(painter = painterResource(loginPics[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit)

        }

        PagerCircleIndicator(state = state, count = loginPics.size,
            modifier = Modifier
                .constrainAs(circleIndicatorRes) {
                    bottom.linkTo(pageRes.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )

        Button(onClick = {
            navController.navigate(LoginPage.REGISTER)
        },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.textButtonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .height(88.dp)
                .width(300.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(R.color.bg_blue_light_start),
                            colorResource(R.color.bg_blue_light_end)
                        )
                    ),
                    shape = RoundedCornerShape(6.dp)
                )
                .constrainAs(registerRes) {
                    top.linkTo(pageRes.bottom, margin = 22.dp)
                    start.linkTo(parent.start)
                    end.linkTo(loginRes.start)
                }){

            Text(text = "注册",
                fontSize = 32.sp,
                color = colorResource(R.color.tv_blue_in_light),
                textAlign = TextAlign.Center)
        }

        Button(onClick = {
            navController.navigate(LoginPage.LOGIN)
        },
            colors = ButtonDefaults.textButtonColors(containerColor = colorResource(R.color.bg_blue_deep_start)),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .height(88.dp)
                .width(300.dp)
                .constrainAs(loginRes) {
                    top.linkTo(pageRes.bottom, margin = 22.dp)
                    start.linkTo(registerRes.end)
                    end.linkTo(parent.end)
                }){

            Text(text = "登录",
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
        }
    }
}