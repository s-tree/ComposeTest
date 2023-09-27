package com.jingxi.library.weiget

import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshDefaults
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Math.abs
import kotlin.math.pow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> PullRefreshAnimLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    items: List<T>,
    itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit
) {
    val refreshPlaceHeight = 88.dp
    val animationViewSize = 40.dp
    val animationPadding: Float
    with(LocalDensity.current) {
        animationPadding =
            (refreshPlaceHeight.toPx() - animationViewSize.toPx()) / 2 + animationViewSize.toPx()
    }

    val state =
        rememberPullRefreshLayoutState(refreshing, onRefresh, refreshingOffset = refreshPlaceHeight)

    Box(Modifier.pullRefreshLayout(state)) {
        LazyColumn(
            //让列表跟随手指滑动
            modifier = modifier.graphicsLayer {
                translationY = state.position
            },
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement,
        ) {
            itemsIndexed(items) { index, item ->
                itemContent(index, item)
            }
        }

        walkBallsView(
            pointSize = 5.dp,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .align(Alignment.TopCenter)
                //让指示器跟随手指滑动
                .graphicsLayer {
                    translationY = state.position - animationPadding
                },
            colors = listOf(
                Color.Green,
                Color.Red,
                Color.Gray
            )
        )
    }
}

//不用看就改个名字而已
@Composable
@ExperimentalMaterialApi
fun rememberPullRefreshLayoutState(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    refreshThreshold: Dp = PullRefreshDefaults.RefreshThreshold,
    refreshingOffset: Dp = PullRefreshDefaults.RefreshingOffset,
): PullRefreshLayoutState {
    require(refreshThreshold > 0.dp) { "The refresh trigger must be greater than zero!" }

    val scope = rememberCoroutineScope()
    val onRefreshState = rememberUpdatedState(onRefresh)
    val thresholdPx: Float
    val refreshingOffsetPx: Float

    with(LocalDensity.current) {
        thresholdPx = refreshThreshold.toPx()
        refreshingOffsetPx = refreshingOffset.toPx()
    }

    val state = remember(scope) {
        PullRefreshLayoutState(scope, onRefreshState, refreshingOffsetPx, thresholdPx)
    }

    SideEffect {
        state.setRefreshing(refreshing)
    }

    return state
}

//不用看就是改个名字并把position的internal去掉
@ExperimentalMaterialApi
fun Modifier.pullRefreshLayout(
    state: PullRefreshLayoutState,
    enabled: Boolean = true
) = inspectable(inspectorInfo = debugInspectorInfo {
    name = "pullRefresh"
    properties["state"] = state
    properties["enabled"] = enabled
}) {
    Modifier.pullRefresh(state::onPull, { state.onRelease() }, enabled)
}

@ExperimentalMaterialApi
class PullRefreshLayoutState internal constructor(
    private val animationScope: CoroutineScope,
    private val onRefreshState: State<() -> Unit>,
    private val refreshingOffset: Float,
    internal val threshold: Float
) {

    val progress get() = adjustedDistancePulled / threshold

    internal val refreshing get() = _refreshing

    //唯一的变化去掉internal
    val position get() = _position

    private val adjustedDistancePulled by derivedStateOf { distancePulled * 0.5f }

    private var _refreshing by mutableStateOf(false)
    private var _position by mutableStateOf(0f)
    private var distancePulled by mutableStateOf(0f)

    internal fun onPull(pullDelta: Float): Float {
        if (this._refreshing) return 0f

        val newOffset = (distancePulled + pullDelta).coerceAtLeast(0f)
        val dragConsumed = newOffset - distancePulled
        distancePulled = newOffset
        _position = calculateIndicatorPosition()
        return dragConsumed
    }

    internal fun onRelease(): Float {
        if (!this._refreshing) {
            if (adjustedDistancePulled > threshold) {
                onRefreshState.value()
            } else {
                animateIndicatorTo(0f)
            }
        }
        distancePulled = 0f
        return 0f
    }

    internal fun setRefreshing(refreshing: Boolean) {
        if (this._refreshing != refreshing) {
            this._refreshing = refreshing
            this.distancePulled = 0f
            animateIndicatorTo(if (refreshing) refreshingOffset else 0f)
        }
    }

    private fun animateIndicatorTo(offset: Float) = animationScope.launch {
        animate(initialValue = _position, targetValue = offset) { value, _ ->
            _position = value
        }
    }

    private fun calculateIndicatorPosition(): Float = when {
        adjustedDistancePulled <= threshold -> adjustedDistancePulled
        else -> {
            val overshootPercent = abs(progress) - 1.0f
            val linearTension = overshootPercent.coerceIn(0f, 2f)
            val tensionPercent = linearTension - linearTension.pow(2) / 4
            val extraOffset = threshold * tensionPercent
            threshold + extraOffset
        }
    }
}