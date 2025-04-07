package com.jingxi.smartlife.pad.util

//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.os.BatteryManager
import android.os.Build
import android.text.TextUtils
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleEventObserver
import com.jingxi.smartlife.pad.compose.R
import java.lang.reflect.Field

class BatteryUtil {
    class BatteryState internal constructor(
        var hasBattery: Boolean = false,
        var batteryHealthy: Boolean = false,
        var batteryCharging: Boolean = false,
        var batteryLevel: Int = 0
    )

    companion object {

        fun getBatteryIcon(state: BatteryState): Int {
            if (state.hasBattery.not() || state.batteryHealthy.not()) {
                return R.mipmap.battery_damage
            }
            val fileName = TextUtils.concat(
                if (state.batteryCharging) "battery_charging_" else "battery_",
                (state.batteryLevel shl 1).toString()
            ).toString()
            try {
                val field: Field = R.mipmap::class.java.getField(fileName)
                return field.getInt(null)
            } catch (e: Exception) {
                e.printStackTrace()
                return R.mipmap.battery_damage
            }
        }
    }

    @Composable
    fun observeBattery(batteryState: BatteryState){
        if(Build.MODEL == "T800C"){
            batteryState.hasBattery = false
            return
        }
        batteryState.hasBattery = true

//        val context = LocalContext.current
//        val lifecycleOwner = LocalLifecycleOwner.current
//
//        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
//        batteryState.batteryCharging = batteryManager.isCharging
//
//        val batteryReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent) {
//
//                val chargingState: Int = intent.getIntExtra(
//                    BatteryManager.EXTRA_STATUS,
//                    BatteryManager.BATTERY_STATUS_UNKNOWN
//                )
//
//                batteryState.batteryCharging = chargingState == BatteryManager.BATTERY_STATUS_CHARGING
//
//                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
//                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0)
//                val fPercent = level.toDouble() / scale.toDouble()
//                var levelPercent = 0
//                /**
//                 * 80%以上算满电
//                 */
//                /**
//                 * 80%以上算满电
//                 */
//                if (fPercent >= 0.8f) {
//                    levelPercent = 5
//                } else if (fPercent >= 0.64f) {
//                    levelPercent = 4
//                } else if (fPercent >= 0.48f) {
//                    levelPercent = 3
//                } else if (fPercent >= 0.32f) {
//                    levelPercent = 2
//                } else if (fPercent >= 0.16f) {
//                    levelPercent = 1
//                }
//                batteryState.batteryLevel = levelPercent
//
//                val health: Int = intent.getIntExtra(
//                    BatteryManager.EXTRA_HEALTH,
//                    BatteryManager.BATTERY_HEALTH_UNKNOWN
//                )
//                batteryState.batteryHealthy = health != BatteryManager.BATTERY_HEALTH_DEAD
//            }
//        }

//        DisposableEffect(LocalLifecycleOwner.current){
//
//            //注册电池广播
//            val batteryFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
//            batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
//            batteryFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
//
//            context.registerReceiver(batteryReceiver, batteryFilter)
//
//            val lifecycleObserver = LifecycleEventObserver { _, event ->
//                if (event == Lifecycle.Event.ON_STOP) {
//                    context.unregisterReceiver(batteryReceiver)
//                }
//            }
//
//            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
//
//            onDispose {
//                context.unregisterReceiver(batteryReceiver)
//            }
//        }
    }
}