package com.jingxi.test_xiaorun.ui.weiget

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jingxi.test_xiaorun.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerCircleIndicator(
    state: PagerState = rememberPagerState(),
    count : Int,
    colors: CircleIndicatorColor = circleIndicatorColors(
        colorResource(R.color.circle_indicator_vis),
        colorResource(R.color.circle_indicator_dis)),
    modifier : Modifier
){
    Row(
        modifier = modifier.height(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        val current = state.currentPage
        for (i in 0 until count){
            val color = if (i == current) {colors.showingColor} else {colors.disableColor}
            val width = if (i == current) {12.dp} else {4.dp}
            Spacer(
                Modifier.width(width).height(4.dp)
                    .background(color = color, shape = RoundedCornerShape(4.dp))
            )
            if(i < count-1){
                Spacer(Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun circleIndicatorColors(showingColor : Color,disableColor: Color):CircleIndicatorColor = CircleIndicatorColor(
    showingColor = showingColor,
    disableColor = disableColor
)

@Immutable
class CircleIndicatorColor internal constructor(
    val showingColor: Color,
    val disableColor: Color
)
