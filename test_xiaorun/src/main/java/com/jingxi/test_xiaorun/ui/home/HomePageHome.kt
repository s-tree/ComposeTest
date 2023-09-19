package com.jingxi.test_xiaorun.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.ui.weiget.statusBar

@Composable
fun HomePageHome(activityNavController: NavController) {
    statusBar(Color.White, isDarkIcon = true, fitSystemWindow = true)

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
    ) {

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
                modifier = Modifier.constrainAs(qrIcon){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = colorResource(id = R.color.color_fff2f2f2))
                .zIndex(2f)
        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(start = 27.dp, end = 38.dp,top = 5.dp),
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

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}