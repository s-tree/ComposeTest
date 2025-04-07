package com.jingxi.smartlife.pad.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import java.text.SimpleDateFormat
import java.util.Calendar

class TimeUtil {

    class TimeState() {
        val timeState = mutableStateOf<String>("")
        val dateState = mutableStateOf<String>("")
    }

    @Composable
    fun observeTime(state: TimeState){
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        updateState(state)

        val timeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                updateState(state)
            }
        }

        DisposableEffect(LocalLifecycleOwner.current){

            //注册电池广播
            val batteryFilter = IntentFilter(Intent.ACTION_TIME_TICK)
            context.registerReceiver(timeReceiver, batteryFilter)

            val lifecycleObserver = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    context.unregisterReceiver(timeReceiver)
                }
            }

            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

            onDispose {
                context.unregisterReceiver(timeReceiver)
            }
        }
    }

    fun updateState(state: TimeState){
        val calendar = Calendar.getInstance()
        state.timeState.value = SimpleDateFormat("HH:mm").format(calendar.time)
        state.dateState.value = SimpleDateFormat("EEEE\nMM月dd日").format(calendar.time)
    }
}