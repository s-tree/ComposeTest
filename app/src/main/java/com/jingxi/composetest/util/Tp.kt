@file:Suppress("NOTHING_TO_INLINE")

package com.jingxi.composetest.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp

class UnitCache{

    companion object{
        var designWidth = 1280
        var screenWidth = 1280
        var designScale = 1f
    }
}

/**
 * 可以做适配用
 * 使用时 直接 .tp就行
 * 1280 为设计图尺寸
 * 1920 为实际尺寸
 */
@Stable
inline val Int.tp: Dp get() = Dp(value = this.toFloat() * UnitCache.designScale)