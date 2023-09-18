package com.jingxi.test_xiaorun.ui.login

import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.EditText
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.jingxi.library.BaseApplication
import com.jingxi.test_xiaorun.R
import com.jingxi.test_xiaorun.constant.Page
import com.jingxi.test_xiaorun.data.request
import com.jingxi.test_xiaorun.filter.InputFilters
import com.jingxi.test_xiaorun.ui.weiget.ProgressButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginLogin(navController: NavController,activityController: NavController){

    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 25.dp, start = 25.dp)
            .background(color = Color.White)
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        val phoneInput = remember {
            mutableStateOf("")
        }

        val passwordInput = remember {
            mutableStateOf("")
        }

        val (backRes, loginTitleRes, phoneTitleRes, phoneLinearRes, checkPhoneRes,phoneLineRes,
            passwordTitleRes, passwordInputRes,passwordLineRes,toRegisterRes,toForgetRes,loginButtonRes) = remember {
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

        Text(text = "登录账号",
            fontSize = 48.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(loginTitleRes) {
                top.linkTo(backRes.bottom, margin = 80.dp)
                start.linkTo(backRes.start, margin = 44.dp)
            })

        Text(text = "手机号",
            fontSize = 28.sp,
            color = colorResource(id = R.color.tv_gray),
            modifier = Modifier.constrainAs(phoneTitleRes) {
                top.linkTo(loginTitleRes.bottom, margin = 109.dp)
                start.linkTo(loginTitleRes.start)
            })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 34.dp, bottom = 34.dp)
                .constrainAs(phoneLinearRes) {
                    top.linkTo(phoneTitleRes.bottom, margin = 6.dp)
                    start.linkTo(loginTitleRes.start)
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

        Text(text = "密码",
            fontSize = 28.sp,
            color = colorResource(id = R.color.tv_gray),
            modifier = Modifier.constrainAs(passwordTitleRes) {
                top.linkTo(phoneLineRes.bottom, margin = 42.dp)
                start.linkTo(loginTitleRes.start)
            })

        /**
         * Compose TextField / BasicTextField 没有 password 模式，使用android View/EditText 来实现
         */
        AndroidView(
            modifier = Modifier
                .padding(top = 34.dp, start = 0.dp, bottom = 34.dp)
                .background(color = Color.White)
                .constrainAs(passwordInputRes) {
                    top.linkTo(passwordTitleRes.bottom, margin = 6.dp)
                    start.linkTo(loginTitleRes.start)
                },
            factory = {context -> EditText(context)},
            update = {
                it.layoutParams.width = LayoutParams.MATCH_PARENT
                it.gravity = Gravity.CENTER_VERTICAL or Gravity.START
                it.textSize = 34f
                it.setTextColor(BaseApplication.instance!!.getColor(R.color.color_ff323232))
                it.hint = "请输入密码"
                it.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                it.doAfterTextChanged {
                    passwordInput.value = it.toString()
                }
                /**
                 * 文本过滤
                 */
                it.filters = Array(1){LengthFilter(16)}
                it.background = BaseApplication.instance!!.getDrawable(R.color.white_trans_ba)
                /**
                 * 手机号输完后自动获取焦点
                 */
                if(phoneInput.value.length == 11){
                    it.requestFocus()
                }
            })

        Spacer(
            Modifier
                .fillMaxWidth()
                .padding(start = 44.dp, end = 60.dp)
                .height(1.dp)
                .background(color = colorResource(id = R.color.bg_line_gray))
                .constrainAs(passwordLineRes) {
                    top.linkTo(passwordInputRes.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        val loginLoadingState = remember{
            mutableStateOf(false)
        }

        ProgressButton(
            state = loginLoadingState,
            progressColor = colorResource(R.color.bg_blue_deep_start),
            onClick = {
                focusManager.clearFocus(true)
                keyboardController?.hide()

                CoroutineScope(Dispatchers.Main).launch {
                    loginLoadingState.value = true
                    val job = async { request() }
                    val result = job.await();
                    println("async 结果 $result")
                    loginLoadingState.value = false
                    activityController.navigate(
                        Page.HOME,
                        NavOptions
                            .Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            },
            enabled = phoneInput.value.length == 11 && passwordInput.value.length >= 6,
            colors = ButtonDefaults.textButtonColors(
                containerColor = colorResource(R.color.bg_blue_deep_start),
                disabledContainerColor = colorResource(id = R.color.bg_blue_light_start)
            ),
            shape = RoundedCornerShape(6.dp),
            layoutModifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(start = 44.dp, end = 60.dp)
                .constrainAs(loginButtonRes) {
                    top.linkTo(passwordInputRes.bottom, margin = 90.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            content = @Composable{
                Text(text = "登录",
                fontSize = 36.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
            }
        )

        Text(
            text = "注册",
            color = colorResource(id = R.color.tv_gray),
            fontSize = 24.sp,
            modifier = Modifier
                .clickable(onClick = {
                    navController.navigate(
                        LoginPage.REGISTER,
                        NavOptions
                            .Builder()
                            .setPopUpTo(LoginPage.LOGIN, inclusive = true)
                            .setLaunchSingleTop(false)
                            .build()
                    )
                })
                .constrainAs(toRegisterRes) {
                    top.linkTo(loginButtonRes.bottom, margin = 40.dp)
                    start.linkTo(parent.start, margin = 45.dp)
                })

        Text(
            text = "忘记密码?",
            color = colorResource(id = R.color.tv_gray),
            fontSize = 24.sp,
            modifier = Modifier
                .clickable(onClick = {
                    navController.navigate(
                        LoginPage.FORGET,
                        NavOptions
                            .Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                })
                .constrainAs(toForgetRes) {
                    top.linkTo(loginButtonRes.bottom, margin = 40.dp)
                    end.linkTo(parent.end, margin = 61.dp)
                })
    }
    
}