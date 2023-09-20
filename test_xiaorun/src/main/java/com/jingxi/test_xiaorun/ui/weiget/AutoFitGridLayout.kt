package com.jingxi.test_xiaorun.ui.weiget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <E> AutoFitGridLayout(modifier:Modifier,data: List<E>,rowCount:Int = 5,colCount:Int = 2,content:@Composable RowScope.(E) -> Unit){

    Column(modifier) {
        val rows = data.size / rowCount + if(data.size % rowCount == 0) {0} else {1}
        for(i in 0..colCount.coerceAtMost(rows - 1)){
            val currentRowStart = i * rowCount
            val currentRowEnd = currentRowStart + rowCount
            Row(Modifier.fillMaxWidth()) {
                for (item in currentRowStart until currentRowEnd){
                    if(item < data.size){
                        content(data[item])
                    }
                    else{
                        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                    }
                }
            }
        }
    }
}

