package com.jingxi.library.weiget

import android.util.Log
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun walkBallsView(pointSize: Dp = 10.dp, ballState: BallState = rememberBallState(),modifier: Modifier = Modifier, colors:List<Color>){
    val animationSpec : InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(durationMillis = 1000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )

    val animateValue =
        if(!ballState.enable) {remember { mutableStateOf(0f) }}
        else rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 200f,
            animationSpec = animationSpec,
        )

    Canvas(modifier = modifier, onDraw = {
        val ballCount = colors.size
        val radius = pointSize.toPx()
        val nowValue = animateValue.value + ballState.offset
        Log.d("walkBallsView: ","nowValue = $nowValue")
        for(i in 0 until 3.coerceAtLeast(ballCount)){
            var progress = nowValue
            if(i == 1){
                progress += 100
            }
            else if(i == 2){
                progress += 150
            }
            val orientation = computerOrientation(progress)
            progress = computerProgress(progress)

            var y = size.height - radius
            if(orientation == -1){
                y = radius + (size.height - radius * 2 ) * ((abs(progress - 50f)) / 50f)
            }
            val current = radius + (size.width - radius * 2) / 100 * progress
            if(i == 0){
                Log.d("walkBallsView: ","currentX = $current + maxX = ${size.width}")
            }
            drawCircle(colors[i],pointSize.toPx(),center = Offset(current,y))
        }
    })
}

fun computerOrientation(progress: Float):Int{
    if(progress < 0){
        return 1
    }
    val h : Int = (progress / 100).toInt()
    if(h % 2 == 1){
        return -1
    }
    else return 1
}
fun computerProgress(progress : Float) : Float{
    if(progress < 0){
        return abs(progress)
    }
    val h : Int = (progress / 100).toInt()
    if(h % 2 == 1){
        return 100 - progress % 100
    }
    else return progress % 100
}

@Composable
fun rememberBallState(offset:Float = 0f,enable:Boolean = true):BallState{
    val scope = rememberCoroutineScope()

    val state = remember(scope) {
        BallState(offset, enable)
    }

    return state
}

class BallState internal constructor(
    var offset: Float,
    var enable: Boolean
)