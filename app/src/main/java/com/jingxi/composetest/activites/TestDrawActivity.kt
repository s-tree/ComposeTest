package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

class TestDrawActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){

                drawPoint(10.dp,
                    Modifier
                        .width(50.dp)
                        .height(30.dp))

                drawBallColor(10.dp,
                    Modifier
                        .width(50.dp)
                        .height(30.dp))

                walkBall(10.dp,
                    Modifier
                        .width(200.dp)
                        .height(50.dp))

                walkBalls(pointSize = 10.dp,
                    ballCount = 3,
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp))

                walkBalls(pointSize = 5.dp,
                    colors = listOf(
                        Color.Green,
                        Color.Red,
                        Color.Blue,
                        Color.Gray
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp))
            }
        }
    }

    @Composable
    fun drawPoint(pointSize:Dp = 10.dp,modifier: Modifier = Modifier){
        Canvas(modifier = modifier, onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(Color.Green,pointSize.toPx(),center = Offset(canvasWidth / 2,canvasHeight / 2))
        })
    }

    @Composable
    fun drawBallColor(pointSize:Dp = 10.dp,modifier: Modifier = Modifier){
        val infiniteTransition = rememberInfiniteTransition()
        val color by infiniteTransition.animateColor(
            initialValue = Color.Red,
            targetValue = Color.Green,
            animationSpec = infiniteRepeatable(
                animation = tween(5000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        Canvas(modifier = modifier, onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(color,pointSize.toPx(),center = Offset(canvasWidth / 2,canvasHeight / 2))
        })
    }

    @Composable
    fun walkBall(pointSize:Dp = 10.dp,modifier: Modifier = Modifier){
        /**
         * 保存当前进度
         */
        val ballState = remember{
            BallState()
        }
        val animationSpec : InfiniteRepeatableSpec<Float> = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )

        val animateValue by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 100f,
            animationSpec = animationSpec,
        )

        Canvas(modifier = modifier, onDraw = {
            val radius = pointSize.toPx()
            var y = size.height - radius
            /**
             * 判断方向
             */
            if(animateValue < ballState.progress){
                y = radius + (size.height - radius * 2 ) * ((abs(animateValue - 50f)) / 50f)
            }
            ballState.progress = animateValue
            val current = radius + (size.width - radius * 2) / 100 * animateValue
            drawCircle(Color.Red,pointSize.toPx(),center = Offset(current,y))
        })
    }

    @Composable
    fun walkBalls(pointSize:Dp = 10.dp,modifier: Modifier = Modifier,ballCount:Int = 1){
        val ballState = remember{
            BallState()
        }

        val animationSpec : InfiniteRepeatableSpec<Float> = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )

        val animateValue by rememberInfiniteTransition().animateFloat(
            initialValue = ballState.progress,
            targetValue = 100f,
            animationSpec = animationSpec,
        )

        Canvas(modifier = modifier, onDraw = {
            val step = 100 / ballCount
            val radius = pointSize.toPx()
            val mainOrientation = if (animateValue < ballState.progress) -1 else 1
            for(i in 1..ballCount){
                /**
                 * 以当前进度为主进度，向前寻找其他进度
                 */
                /**
                 * 当前进度方向
                 */
                var orientation = mainOrientation

                /**
                 * 当前进度
                 */
                var progress = 0f;
                if(orientation == 1){
                    /**
                     * 主进度为正，则相加也用正的
                     */
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
                    /**
                     * 主进度为负，则相加也用负的
                     */
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
                drawCircle(Color.Red,pointSize.toPx(),center = Offset(current,y))
            }
            ballState.progress = animateValue
        })
    }

    @Composable
    fun walkBalls(pointSize:Dp = 10.dp,modifier: Modifier = Modifier,colors:List<Color>){
        val ballState = remember{
            BallState()
        }

        val animationSpec : InfiniteRepeatableSpec<Float> = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
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
}

data class BallState(var progress:Float = 0f)