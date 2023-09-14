package com.jingxi.test_xiaorun.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun countDown(time : Int) = flow{
    var count = time;
    while (count >= 0){
        emit(count)
        count--
        delay(1000)
    }
}