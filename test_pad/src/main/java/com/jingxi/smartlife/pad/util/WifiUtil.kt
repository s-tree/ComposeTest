package com.jingxi.smartlife.pad.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Parcelable
import android.text.TextUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.jingxi.smartlife.pad.compose.R
import java.lang.reflect.Field

class WifiUtil {

    class WifiState internal constructor(
        var connected: Boolean = false,
        var rssi: Int = 0
    )

    companion object{

        fun getRssi(context: Context): Int {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            //获得信号强度值
            val level = wifiInfo.rssi
            //根据获得的信号强度发送信息
            return if (level <= 0 && level >= -50) { //信号最好
                1
            } else if (level < -50 && level >= -70) { //信号较好
                2
            } else if (level < -70 && level >= -80) { //信号一般
                3
            } else if (level < -80 && level >= -100) { //信号较差
                4
            } else { //无信号
                5
            }
        }
        fun getWifiIcon(state: WifiState): Int {
            if(state.connected.not()){
                return R.mipmap.wifi_0
            }
            val fileName = TextUtils.concat("wifi_", (5 - state.rssi).toString()).toString()
            try {
                val field: Field = R.mipmap::class.java.getField(fileName)
                return field.getInt(null)
            } catch (e: Exception) {
                e.printStackTrace()
                return R.mipmap.wifi_0
            }
        }
    }

    @Composable
    fun observeWifi(wifiState: WifiState){
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        val wifiReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                if (WifiManager.WIFI_STATE_CHANGED_ACTION == intent.action) {
                    val connectState = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_DISABLED
                    )
                    if (connectState == WifiManager.WIFI_STATE_DISABLED
                        || connectState == WifiManager.WIFI_STATE_DISABLING) {
                        wifiState.connected = false
                        return
                    }
                }

                if (WifiManager.NETWORK_STATE_CHANGED_ACTION == intent.action) {
                    val parcelableExtra =
                        intent.getParcelableExtra<Parcelable>(WifiManager.EXTRA_NETWORK_INFO)
                    if (null != parcelableExtra) {
                        val networkInfo = parcelableExtra as NetworkInfo
                        val state = networkInfo.state
                        val isConnected = state == NetworkInfo.State.CONNECTED //当然，这边可以更精确的确定状态
                        if (isConnected.not()) {
                            wifiState.connected = false
                            return
                        }
                    }
                }

                if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
                    val info =
                        intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
                    if (info != null) {
                        if (info.state != NetworkInfo.State.CONNECTED) {
                            wifiState.connected = false
                            return
                        }
                    }
                }
                wifiState.connected = true
                wifiState.rssi = getRssi(context)
            }
        }

        DisposableEffect(LocalLifecycleOwner.current){

            //注册电池广播
            val wiFiFilter = IntentFilter()
            wiFiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            wiFiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
            wiFiFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            wiFiFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)

            context.registerReceiver(wifiReceiver, wiFiFilter)

            val lifecycleObserver = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    context.unregisterReceiver(wifiReceiver)
                }
            }

            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

            onDispose {
                context.unregisterReceiver(wifiReceiver)
            }
        }
    }
}