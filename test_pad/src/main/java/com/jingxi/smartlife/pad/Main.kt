package com.jingxi.smartlife.pad

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jingxi.smartlife.pad.compose.R
import com.jingxi.smartlife.pad.util.BatteryUtil
import com.jingxi.smartlife.pad.util.CardListUtil
import com.jingxi.smartlife.pad.util.DoorUtil
import com.jingxi.smartlife.pad.util.EthUtil
import com.jingxi.smartlife.pad.util.TimeUtil
import com.jingxi.smartlife.pad.util.WeatherUtil
import com.jingxi.smartlife.pad.util.WifiUtil
import java.lang.Integer.min

class Main {

    @Composable
    fun ui(){
        ConstraintLayout(
            Modifier
                .fillMaxSize()){

            Image(
                painter = painterResource(id = R.mipmap.new_),
                contentDescription = null,
                Modifier.fillMaxSize())

            val (weatherLeft,weatherRight,weatherSpace) = createRefs()

            val (batteryIcon,wifiIcon,doorIcon,unitIcon,managerIcon,eth0Icon,eth1Icon) = createRefs()
            val (cityText,templateText,templateIcon,timeLayout,timeText,weekText,clockText) = createRefs()
            val (familyIcon,familyName,cardList,qrcode,qrText) = createRefs()

            val batteryState = remember{
                BatteryUtil.BatteryState()
            }.apply {
                BatteryUtil().observeBattery(this)
            }

            val wifiState = remember{
                WifiUtil.WifiState()
            }.apply {
                WifiUtil().observeWifi(this)
            }

            val doorState = remember{
                DoorUtil.DoorState()
            }

            val ethState = remember {
                EthUtil.EthState()
            }.apply {
                EthUtil().observeEth(this)
            }

            val weatherState = remember {
                WeatherUtil.WeatherState()
            }.apply {
                WeatherUtil().observeWeather(this)
            }

            val scope = rememberCoroutineScope()
            val timeState = remember(scope){
                TimeUtil.TimeState()
            }.apply {
                TimeUtil().observeTime(this)
            }

            Image(
                painter = painterResource(id = BatteryUtil.getBatteryIcon(batteryState)),
                contentDescription = null,
                Modifier
                    .width(if (batteryState.hasBattery.not()) 0.dp else 32.dp)
                    .height(22.dp)
                    .constrainAs(batteryIcon) {
                        top.linkTo(parent.top, 26.dp)
                        start.linkTo(parent.start, 26.dp)
                    }
            )

            Image(
                painter = painterResource(id = WifiUtil.getWifiIcon(wifiState)),
                contentDescription = null,
                Modifier
                    .width(32.dp)
                    .height(22.dp)
                    .constrainAs(wifiIcon) {
                        top.linkTo(batteryIcon.top)
                        start.linkTo(batteryIcon.end, 10.dp)
                    }
            )

            Image(
                painter = painterResource(id = DoorUtil.getOnDoorIcon(doorState)),
                contentDescription = null,
                Modifier
                    .width(32.dp)
                    .height(22.dp)
                    .constrainAs(doorIcon) {
                        top.linkTo(batteryIcon.top)
                        start.linkTo(wifiIcon.end, 10.dp)
                    }
            )

            Image(
                painter = painterResource(id = DoorUtil.getOutDoorIcon(doorState)),
                contentDescription = null,
                Modifier
                    .width(32.dp)
                    .height(22.dp)
                    .constrainAs(unitIcon) {
                        top.linkTo(batteryIcon.top)
                        start.linkTo(doorIcon.end, 10.dp)
                    }
            )

            Image(
                painter = painterResource(id = DoorUtil.getManagerIcon(doorState)),
                contentDescription = null,
                Modifier
                    .width(32.dp)
                    .height(22.dp)
                    .constrainAs(managerIcon) {
                        top.linkTo(batteryIcon.top)
                        start.linkTo(unitIcon.end, 10.dp)
                    }
            )

            if(ethState.eth0Enable.value){
                Image(
                    painter = painterResource(id = if(ethState.eth0Connected.value) R.mipmap.icon_eth_on else R.mipmap.icon_eth_off),
                    contentDescription = null,
                    Modifier
                        .width(32.dp)
                        .height(22.dp)
                        .constrainAs(eth0Icon) {
                            top.linkTo(batteryIcon.top)
                            start.linkTo(managerIcon.end, 10.dp)
                        }
                )
            }

            if(ethState.eth1Enable.value){
                Image(
                    painter = painterResource(id = if(ethState.eth1Connected.value) R.mipmap.icon_eth_on else R.mipmap.icon_eth_off),
                    contentDescription = null,
                    Modifier
                        .width(32.dp)
                        .height(22.dp)
                        .constrainAs(eth1Icon) {
                            top.linkTo(batteryIcon.top)
                            start.linkTo(eth0Icon.end, 10.dp)
                        }
                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(1.dp)
                    .constrainAs(weatherRight) {
                        top.linkTo(parent.top, 26.dp)
                        end.linkTo(parent.end, 26.dp)
                    }
            )

            if(weatherState.city.isNotEmpty()){
                Text(
                    text = weatherState.city,
                    Modifier.constrainAs(cityText) {
                        top.linkTo(weatherRight.top)
                        end.linkTo(weatherRight.start)
                    },
                    fontSize = 17.6.sp,
                    color = colorResource(id = R.color.color_ff323232)
                )

                Text(
                    text = weatherState.temperature,
                    Modifier
                        .constrainAs(templateText) {
                        top.linkTo(cityText.bottom,6.dp)
                        end.linkTo(cityText.end)
                    },
                    fontSize = 17.6.sp,
                    color = colorResource(id = R.color.color_ff323232)
                )

                Image(
                    painter = painterResource(id = WeatherUtil.getWeatherIcon(weatherState)),
                    contentDescription = null,
                    Modifier
                        .width(57.dp)
                        .height(42.dp)
                        .constrainAs(templateIcon) {
                            top.linkTo(cityText.top)
                            end.linkTo(templateText.start, 12.dp)
                        }
                )

                Spacer(modifier =
                Modifier
                    .width(19.dp)
                    .constrainAs(weatherLeft) {
                        top.linkTo(templateIcon.top)
                        end.linkTo(templateIcon.start, 18.dp)
                    }
                )

                Spacer(modifier =
                Modifier
                    .width(1.dp)
                    .background(colorResource(id = R.color.color_ff323232))
                    .constrainAs(weatherSpace) {
                        top.linkTo(cityText.top)
                        bottom.linkTo(templateText.bottom)
                        end.linkTo(templateIcon.start, 18.dp)
                        //高度填充约束中
                        height = Dimension.fillToConstraints
                    }
                )

            }

            val weatherLayout = createStartBarrier(weatherRight,weatherLeft)

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                .constrainAs(timeLayout){
                    top.linkTo(weatherRight.top)
                    end.linkTo(weatherLayout)
                }){

                Text(
                    text = timeState.timeState.value,
                    style = TextStyle.Default.copy(
                        platformStyle = PlatformTextStyle(
//                            fontFamily = FontFamily.Default,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 43.sp
                            includeFontPadding = false
                        )
                    ),
                    fontSize = 43.sp,
                    color = colorResource(id = R.color.color_ff323232)
                )

                Spacer(modifier = Modifier.width(20.dp))
                
                Text(
                    text = timeState.dateState.value,
                    fontSize = 17.6.sp,
                    color = colorResource(id = R.color.color_ff323232)
                )
            }

            Image(
                painter = painterResource(id = R.mipmap.mrtx),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(53.dp)
                    .height(53.dp)
                    .clip(CircleShape)
                    .constrainAs(familyIcon) {
                        start.linkTo(parent.start, 58.dp)
                        top.linkTo(batteryIcon.bottom, 72.dp)
                    }
            )

            Text(
                text = "未注册",
                fontSize = 20.sp,
                color = colorResource(id = R.color.color_ff323232),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(familyName){
                        start.linkTo(familyIcon.end,24.dp)
                        top.linkTo(familyIcon.top)
                        bottom.linkTo(familyIcon.bottom)
                    }
            )

            val qrState = remember{
                mutableStateOf(false)
            }

            Image(
                painter = painterResource(id = R.mipmap.icon_code),
                contentDescription = null,
                Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .constrainAs(qrcode) {
                        top.linkTo(familyIcon.top)
                        start.linkTo(qrText.start)
                        end.linkTo(qrText.end)
                    }
                    .clickable { qrState.value = true }
            )

            Text(
                text = "家庭二维码",
                fontSize = 16.sp,
                color = colorResource(id = R.color.color_ff323232),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(qrText){
                        top.linkTo(qrcode.bottom, 10.dp)
                        end.linkTo(parent.end, 25.dp)
                    }
            )

            val cardArray = CardListUtil.getCardList()
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cardList) {
                        start.linkTo(familyIcon.start)
                        top.linkTo(familyIcon.bottom, 34.dp)
                    }){
                items(cardArray.size / 2 + (if (cardArray.size % 2 == 0) 0 else 1)){
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        userScrollEnabled = false){
                        val position = it * 2
                        items(count = min(2,cardArray.size - position)){
                            Image(
                                painter = painterResource(id = cardArray[position + it].cardIcon),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(210.dp)
                                    .height(210.dp)
                            )
                        }
                    }
                }
            }

            if(qrState.value){
                qrDialog(qrState)
            }
        }
    }

}

@Preview
@Composable
fun preview(){
    Main().ui()
}

@Composable
fun qrDialog(state: MutableState<Boolean>){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xA0000000))
            .clickable (
                remember { MutableInteractionSource() },
                indication = null,
                onClick = { state.value = false }
            )){

        ConstraintLayout(
            Modifier
                .width(800.dp)
                .height(480.dp)) {

            Image(
                painter = painterResource(id = R.mipmap.bg_family_code),
                contentDescription = null,
                Modifier.fillMaxSize())

            val (nameLinear,qrTipLayout,familyLogo) = createRefs()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(nameLinear) {
                        top.linkTo(parent.top,147.dp)
                        start.linkTo(parent.start,59.dp)
                    }) {

                Text(
                    text = "家庭二维码",
                    fontSize = 48.sp,
                    color = colorResource(id = R.color.black_010100)
                )

                Text(
                    text = "扫码加入家庭",
                    fontSize = 40.sp,
                    color = colorResource(id = R.color.black_010100)
                )
            }

            Text(
                text = stringResource(id = R.string.qr_family_tip),
                fontSize = 16.sp,
                color = colorResource(id = R.color.black_010100),
                lineHeight = 19.sp,
                modifier = Modifier
                    .constrainAs(qrTipLayout) {
                        top.linkTo(nameLinear.bottom, 9.dp)
                        start.linkTo(nameLinear.start)
                        end.linkTo(nameLinear.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Image(
                painter = painterResource(id = R.mipmap.icon_family_logo),
                contentDescription = null,
                Modifier
                    .width(305.dp)
                    .height(319.dp)
                    .constrainAs(familyLogo) {
                        top.linkTo(parent.top, 75.dp)
                        start.linkTo(parent.start, 305.dp)
                    }
            )

            val (qrRightBg,qrRightIcon,qrRightTip) = createRefs()
            Image(
                painter = painterResource(id = R.mipmap.bg_blur_family_code),
                contentDescription = null,
                Modifier
                    .width(345.dp)
                    .height(352.dp)
                    .constrainAs(qrRightBg) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 53.dp)
                        bottom.linkTo(parent.bottom)
                    }
            )


        }
    }
}
