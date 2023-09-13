package com.jingxi.test_xiaorun.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRegister(navController: NavController){
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, start = 25.dp)
            .background(color = Color.White)){

        var phoneInput = remember {
            mutableStateOf("")
        }
        val(backRes,registerTitleRes,phoneTitleRes,phoneLinearRes,codeTitleRes,codeInputRes,codeGetRes,toLoginRes) = remember {
                createRefs()
        }

        val startLinear = createStartBarrier(margin = 69.dp)
        val endLinear = createEndBarrier(margin = 60.dp)

        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clickable(
                    onClick = {
                        navController.popBackStack()
                    }
                )
                .constrainAs(backRes) {
                    top.linkTo(parent.top)
                    start.linkTo(startLinear)
                    end.linkTo(parent.end)
                })

        Text(text = "注册账号", fontSize = 48.sp,color = Color.Black,
        modifier = Modifier.constrainAs(registerTitleRes){
            top.linkTo(backRes.bottom, margin = 80.dp)
            start.linkTo(startLinear)
        })

        Text(text = "手机号", fontSize = 28.sp,color = colorResource(id = R.color.tv_gray),
            modifier = Modifier.constrainAs(phoneTitleRes){
                top.linkTo(registerTitleRes.bottom, margin = 109.dp)
                start.linkTo(startLinear)
            })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(phoneLinearRes) {
                    top.linkTo(phoneTitleRes.bottom, margin = 6.dp)
                    start.linkTo(startLinear)
                    end.linkTo(endLinear)
                }
        ){
            Text(text = "+86",
            color = Color.Black,
            fontSize = 34.sp)

            Spacer(modifier = Modifier.width(5.dp))

            Image(painter = painterResource(id = R.mipmap.icon_to_bottom_gray), contentDescription = "",
                Modifier
                    .width(15.dp)
                    .height(15.dp))

            Spacer(modifier = Modifier.width(24.dp))

            var aaa = remember {
                mutableStateOf("")
            }
            TextField(
                value = aaa,
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = { aaa = it }
            )
        }
    }
}

//,
//textStyle = TextStyle(fontSize = 34.sp, textAlign = TextAlign.Start),
//label = PhonePlace(),
//placeholder = PhonePlace(),
//singleLine = true, maxLines = 1,
//color = TextFieldDefaults.textFieldColors(
//textColor = colorResource(id = R.color.color_ff323232),
//placeholderColor = colorResource(id = R.color.color_ff999999)
//),
//keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)

@Composable
fun PhonePlace(){
    Text(text = "请输入您的手机号码")
}