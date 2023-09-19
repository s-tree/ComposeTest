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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.data.request
import com.jingxi.test_xiaorun.filter.InputFilters
import com.jingxi.test_xiaorun.ui.weiget.ProgressButton
import com.jingxi.test_xiaorun.util.ToastUtil
import com.jingxi.test_xiaorun.util.countDown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginRegister(navController: NavController) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 25.dp, start = 25.dp)
            .background(color = Color.White)
    ) {
        val phoneInput = remember {
            mutableStateOf("")
        }

        val codeInput = remember {
            mutableStateOf("")
        }

        val codeCheck = remember {
            mutableStateOf(0)
        }

        var hasCheckedCode = remember {
            mutableStateOf(false)
        }

        val (backRes, registerTitleRes, phoneTitleRes, phoneLinearRes, checkPhoneRes,phoneLineRes,
            codeTitleRes, codeInputRes,codeCheckRes,codeLineRes,toRegisterRes,toLoginRes) = remember {
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

        Text(text = "注册账号",
            fontSize = 48.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(registerTitleRes) {
                top.linkTo(backRes.bottom, margin = 80.dp)
                start.linkTo(backRes.start, margin = 44.dp)
            })

        Text(text = "手机号",
            fontSize = 28.sp,
            color = colorResource(id = R.color.tv_gray),
            modifier = Modifier.constrainAs(phoneTitleRes) {
                top.linkTo(registerTitleRes.bottom, margin = 109.dp)
                start.linkTo(registerTitleRes.start)
        })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 34.dp, bottom = 34.dp)
                .constrainAs(phoneLinearRes) {
                    top.linkTo(phoneTitleRes.bottom, margin = 6.dp)
                    start.linkTo(registerTitleRes.start)
                }) {
            Text(
                text = "+86",
                color = Color.Black,
                fontSize = 34.sp
            )

            Spacer(modifier = Modifier.width(5.dp))

            Image(
                painter = painterResource(id = R.mipmap.icon_to_bottom_gray),
                contentDescription = "",
                Modifier
                    .width(15.dp)
                    .height(15.dp)
            )

            Spacer(modifier = Modifier.width(24.dp))

            BasicTextField(
                value = phoneInput.value,
                modifier = Modifier
                    .background(color = Color.White),
                onValueChange = {
                    var str = InputFilters.lengthFilter(it, 11)
                    str = InputFilters.numberFilter(str)
                    phoneInput.value = str
                },
                textStyle = TextStyle(fontSize = 34.sp, textAlign = TextAlign.Start,color = colorResource(id = R.color.color_ff323232)),
                singleLine = true, maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                cursorBrush = SolidColor(colorResource(id = R.color.color_ff999999)),
                decorationBox = {
                    if(phoneInput.value.isEmpty()){
                        Text(text = "请输入您的手机号码",
                            textAlign = TextAlign.Start,
                            fontSize = 34.sp,
                            color = colorResource(id = R.color.color_ff999999))
                    }
                    it()
                }
            )
        }

        if (phoneInput.value.length == 11) {
            Image(painter = painterResource(id = R.mipmap.icon_check_success),
                contentDescription = "",
                modifier = Modifier
                    .width(30.dp)
                    .height(22.dp)
                    .constrainAs(checkPhoneRes) {
                        top.linkTo(phoneLinearRes.top)
                        bottom.linkTo(phoneLinearRes.bottom)
                        end.linkTo(parent.end, margin = 60.dp)
                    })
        }

        Spacer(
            Modifier
                .fillMaxWidth()
                .padding(start = 44.dp, end = 60.dp)
                .height(1.dp)
                .background(color = colorResource(id = R.color.bg_line_gray))
                .constrainAs(phoneLineRes) {
                    top.linkTo(phoneLinearRes.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        Text(text = "验证码",
            fontSize = 28.sp,
            color = colorResource(id = R.color.tv_gray),
            modifier = Modifier.constrainAs(codeTitleRes) {
                top.linkTo(phoneLineRes.bottom, margin = 42.dp)
                start.linkTo(registerTitleRes.start)
            })

        BasicTextField(
            value = codeInput.value,
            modifier = Modifier
                .padding(top = 34.dp, start = 0.dp, bottom = 34.dp)
                .background(color = Color.White)
                .constrainAs(codeInputRes) {
                    top.linkTo(codeTitleRes.bottom, margin = 6.dp)
                    start.linkTo(registerTitleRes.start)
                },
            onValueChange = {
                var str = InputFilters.lengthFilter(it, 4)
                str = InputFilters.numberFilter(str)
                codeInput.value = str
            },
            textStyle = TextStyle(fontSize = 34.sp, textAlign = TextAlign.Start,color = colorResource(id = R.color.color_ff323232)),
            singleLine = true, maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            cursorBrush = SolidColor(colorResource(id = R.color.color_ff999999)),
            decorationBox = {
                if(codeInput.value.isEmpty()){
                    Text(text = "请输入验证码",
                        textAlign = TextAlign.Start,
                        fontSize = 34.sp,
                        color = colorResource(id = R.color.color_ff999999))
                }
                it()
            }
        )

        val codeCheckLoadingState = remember{
            mutableStateOf(false)
        }

        ProgressButton(
            state = codeCheckLoadingState,
            progressColor = colorResource(R.color.bg_blue_deep_start),
            onClick = {
                hasCheckedCode.value = true
                CoroutineScope(Dispatchers.Default).launch {
                    codeCheckLoadingState.value = true
                    val job = async { request() }
                    val result = job.await();
                    if(result == "Success"){
                        countDown(60)
                            .onEach { codeCheck.value = it }
                            .launchIn(MainScope())
                    }
                    codeCheckLoadingState.value = false
                }
            },
            enabled = codeCheck.value == 0,
            colors = ButtonDefaults.textButtonColors(
                containerColor = colorResource(R.color.bg_blue_deep_start),
                disabledContainerColor = colorResource(id = R.color.bg_blue_light_start)
            ),
            shape = RoundedCornerShape(6.dp),
            layoutModifier = Modifier
                .width(200.dp)
                .height(68.dp)
                .constrainAs(codeCheckRes) {
                    top.linkTo(codeInputRes.top)
                    bottom.linkTo(codeInputRes.bottom)
                    end.linkTo(parent.end, margin = 60.dp)
                }){
            Text(text = if (codeCheck.value == 0) { "获取验证码"} else {codeCheck.value.toString() + "秒"},
                fontSize = 26.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
        }

        Spacer(
            Modifier
                .fillMaxWidth()
                .padding(start = 44.dp, end = 60.dp)
                .height(1.dp)
                .background(color = colorResource(id = R.color.bg_line_gray))
                .constrainAs(codeLineRes) {
                    top.linkTo(codeInputRes.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        val registerLoadingState = remember{
            mutableStateOf(false)
        }

        ProgressButton(
            state = registerLoadingState,
            progressColor = colorResource(R.color.bg_blue_deep_start),
            onClick = {
                if(!hasCheckedCode.value){
                    ToastUtil.show("请先获取验证码")
                    return@ProgressButton
                }
                CoroutineScope(Dispatchers.Default).launch {
                    registerLoadingState.value = true
                    val job = async { request() }
                    val result = job.await();
                    println("async 结果 $result")
                    registerLoadingState.value = false
                }
            },
            enabled = phoneInput.value.length == 11 && codeInput.value.length == 4,
            colors = ButtonDefaults.textButtonColors(
                containerColor = colorResource(R.color.bg_blue_deep_start),
                disabledContainerColor = colorResource(id = R.color.bg_blue_light_start)
            ),
            shape = RoundedCornerShape(6.dp),
            layoutModifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(start = 44.dp, end = 60.dp)
                .constrainAs(toRegisterRes) {
                    top.linkTo(codeInputRes.bottom, margin = 90.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            content = @Composable{Text(text = "下一步",
                fontSize = 36.sp,
                color = Color.White,
                textAlign = TextAlign.Center)}
        )

        Text(
            text = "已有账号，去登录",
            color = colorResource(id = R.color.tv_gray),
            fontSize = 24.sp,
            modifier = Modifier
                .clickable(onClick = {
                    navController.navigate(
                        LoginPage.LOGIN,
                        NavOptions
                            .Builder()
                            .setPopUpTo(LoginPage.REGISTER,inclusive = true)
                            .setLaunchSingleTop(false)
                            .build()
                    )
                })
                .constrainAs(toLoginRes) {
                    top.linkTo(toRegisterRes.bottom, margin = 40.dp)
                    end.linkTo(parent.end, margin = 61.dp)
                })
    }
}
