package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class TestViewModelActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel : PointViewModel = viewModel(modelClass = PointViewModel::class.java)

            /**
             * 如果没有这行，以下三个Text都无法刷新，因为未observe
             * 如果加上这行，以下三个Text都会刷新，不仅仅是count3,因为compose刷新会将state部分刷新，此时另外两个count都有变化，所以刷新
             */
            val viewModel3State = viewModel.count3.observeAsState()

            Column(Modifier.fillMaxSize()) {

                Text(text = viewModel.count1.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center)

                Text(text = viewModel.count2.value.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center)

                Text(text = viewModel3State.value.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center)

                Text(text = "plus",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable(onClick = {
                            viewModel.plus()
                        }),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center)
            }

        }
    }
}

open class PointViewModel : ViewModel(){

    var count1 = 0
    var count2 = MutableLiveData(0)
    var count3 = MutableLiveData(0)

    fun plus(){
        count1 ++
        count2.value = count2.value?.plus(1)
        count3.value = count3.value?.plus(1)
    }
}