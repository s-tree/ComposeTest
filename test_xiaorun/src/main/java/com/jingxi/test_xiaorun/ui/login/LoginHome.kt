package com.jingxi.test_xiaorun.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginHome(navController: NavController){
    ConstraintLayout(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        val pageRes = createRef()
        val loginPics = listOf(R.mipmap.login_page_1,R.mipmap.login_page_2,R.mipmap.login_page_3)
        val state = rememberPagerState(0);
        HorizontalPager(loginPics.size,
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 0.75f)
                .constrainAs(pageRes){
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

        val circleIndicatorRes = createRef()
        PagerCircleIndicator(state = state, count = loginPics.size,
            modifier = Modifier.constrainAs(circleIndicatorRes){
                bottom.linkTo(pageRes.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.fillMaxWidth()
        )

        var spaceRes = createRef()
        Spacer(Modifier.height(22.dp)
            .constrainAs(spaceRes){
                top.linkTo(pageRes.bottom)
            })

        val bottomRes = createRef()
        Row (
            Modifier
            .fillMaxWidth()
            .height(88.dp)
            .constrainAs(bottomRes){
                top.linkTo(spaceRes.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
        },
        horizontalArrangement = Arrangement.Center){
            Spacer(Modifier.width(20.dp))

            // 使用Modifier 来设置渐变背景，将button当前的color设置为透明
            Button(onClick = {
                navController.navigate(LoginPage.REGISTER)
            },
                colors = ButtonDefaults.textButtonColors(containerColor = Color.Transparent),
                modifier = Modifier
                .height(88.dp)
                .width(300.dp)
                    .background(brush = Brush.linearGradient(
                        colors = listOf(colorResource(R.color.bg_blue_light_start),colorResource(R.color.bg_blue_light_end))),
                        shape = RoundedCornerShape(6.dp)
                    ))
            {

                Text(text = "注册",
                    fontSize = 32.sp,
                    color = colorResource(R.color.tv_blue_in_light),
                    textAlign = TextAlign.Center)
            }

            Spacer(Modifier.width(20.dp))

            // 使用Button 的color属性设置背景
            Button(onClick = {
                navController.navigate(LoginPage.REGISTER)
            },
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(R.color.bg_blue_deep_start)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .height(88.dp)
                    .width(300.dp)){

                Text(text = "登录",
                    fontSize = 32.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center)
            }

            Spacer(Modifier.width(20.dp))
        }
    }
}