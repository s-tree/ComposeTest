package com.jingxi.test_xiaorun.data

import kotlinx.coroutines.delay

suspend fun request():String{
    delay(3000)
    return "Success";
}