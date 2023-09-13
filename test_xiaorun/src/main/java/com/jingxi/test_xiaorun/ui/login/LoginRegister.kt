package com.jingxi.test_xiaorun.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.filter.LengthFilter
import com.jingxi.test_xiaorun.filter.NumberFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRegister(navController: NavController){
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, start = 25.dp)
            .background(color = Color.White)){

        val phoneInput = remember {
            mutableStateOf("")
        }
        val(backRes,registerTitleRes,phoneTitleRes,phoneLinearRes,checkPhoneRes,codeTitleRes,codeInputRes,codeGetRes,toLoginRes) = remember {
                createRefs()
        }

        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "",
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .clickable(
                    onClick = {
                        navController.popBackStack()
                    }
                )
                .constrainAs(backRes) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })

        Text(text = "注册账号", fontSize = 48.sp,color = Color.Black,
        modifier = Modifier.constrainAs(registerTitleRes){
            top.linkTo(backRes.bottom, margin = 80.dp)
            start.linkTo(backRes.start, margin = 69.dp)
        })

        Text(text = "手机号", fontSize = 28.sp,color = colorResource(id = R.color.tv_gray),
            modifier = Modifier.constrainAs(phoneTitleRes){
                top.linkTo(registerTitleRes.bottom, margin = 109.dp)
                start.linkTo(registerTitleRes.start)
            })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(phoneLinearRes) {
                    top.linkTo(phoneTitleRes.bottom, margin = 6.dp)
                    start.linkTo(registerTitleRes.start)
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

            TextField(
                value = phoneInput.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                onValueChange = {
                    var str = LengthFilter(it,11)
                    str = NumberFilter(str)
                    phoneInput.value = str
                },
                shape = RectangleShape,
                textStyle = TextStyle(fontSize = 34.sp, textAlign = TextAlign.Start),
                singleLine = true, maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = @Composable{ Text(text = "请输入您的手机号码")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    textColor = colorResource(id = R.color.color_ff323232),
                    placeholderColor = colorResource(id = R.color.color_ff999999),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
        }

        if(phoneInput.value.length == 11){
            Image(painter = painterResource(id = R.mipmap.icon_check_success),
                contentDescription = "",
                modifier = Modifier.width(30.dp).height(22.dp).constrainAs(checkPhoneRes){
                    top.linkTo(phoneLinearRes.top)
                    bottom.linkTo(phoneLinearRes.bottom)
                    end.linkTo(phoneLinearRes.end)
                })
        }
    }
}
