package com.jingxi.library.foundation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * 覆盖了 Modifier.clickable 屏蔽了点击时的波纹效果
 */
fun Modifier.noInterClick(onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember {
            MutableInteractionSource()
        },
        indication = null,
        onClick = onClick
    )
}