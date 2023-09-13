package com.jingxi.composetest.composeui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jingxi.composetest.R

@Composable
fun firstView(click : () -> Unit){
    MaterialTheme{
        ConstraintLayout(
            Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .fillMaxHeight()) {

            ConstraintLayout(
                Modifier
                    .background(Color.White)
                    .padding(start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
                    .height(88.dp)) {

                val (backIcon) = createRefs()
                Image(painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(backIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(40.dp)
                        .clickable(onClick = click),
                    contentScale = ContentScale.Fit)

                val space = createRef()
                Spacer(modifier = Modifier
                    .constrainAs(space) {
                        start.linkTo(backIcon.end)
                    }
                    .width(10.dp))

                val backTv = createRef()
                Text(text = "返回",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(backTv){
                        start.linkTo(space.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                        .clickable (onClick = click))

                val title = createRef()
                Text(text = "FirstActivity",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(title){
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    })
            }
        }
    }
}

@Preview
@Composable
fun preview(){
    firstView(click = {})
}