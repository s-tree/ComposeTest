package com.jingxi.library.weiget

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun walkBallsView(pointSize: Dp = 10.dp, modifier: Modifier = Modifier, colors:List<Color>){
    val ballState = remember{
        BallState()
    }

    val animationSpec : InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(durationMillis = 1000, easing = LinearEasing),
        repeatMode = RepeatMode.Reverse
    )

    val animateValue by rememberInfiniteTransition().animateFloat(
        initialValue = ballState.progress,
        targetValue = 100f,
        animationSpec = animationSpec,
    )

    Canvas(modifier = modifier, onDraw = {
        val ballCount = colors.size
        val step = 100 / ballCount
        val radius = pointSize.toPx()
        val mainOrientation = if (animateValue < ballState.progress) -1 else 1
        for(i in 1..ballCount){
            var orientation = mainOrientation
            var progress = 0f;
            if(orientation == 1){
                progress = (i - 1) * step + animateValue
                if (progress > 100){
                    val temp = progress %100
                    progress -= 100
                    progress -= temp
                    progress += 100 - temp
                    orientation *= -1
                }
            }
            else {
                progress = animateValue - (i - 1) * step
                if (progress < 0){
                    progress = abs(progress)
                    orientation *= -1
                }
            }

            var y = size.height - radius
            if(orientation == -1){
                y = radius + (size.height - radius * 2 ) * ((abs(progress - 50f)) / 50f)
            }
            val current = radius + (size.width - radius * 2) / 100 * progress
            drawCircle(colors[i - 1],pointSize.toPx(),center = Offset(current,y))
        }
        ballState.progress = animateValue
    })
}

data class BallState(var progress:Float = 0f)