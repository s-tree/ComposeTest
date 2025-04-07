package com.jingxi.smartlife.pad.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.jingxi.library.util.FileUtil
import java.net.NetworkInterface
import java.util.Enumeration

class EthUtil {

    class EthState {
        val eth0Enable = mutableStateOf(false)
        val eth1Enable = mutableStateOf(false)
        val eth0Connected = mutableStateOf(false)
        val eth1Connected = mutableStateOf(false)
    }

    companion object{
        fun supportInterface(interfaceName: String): Boolean{
            try {
                val nl: Enumeration<NetworkInterface>? = NetworkInterface.getNetworkInterfaces()
                if(nl == null){
                    return false
                }
                while (nl.hasMoreElements()) {
                    val networkInterface = nl.nextElement()
                    if (networkInterface.name.contains(interfaceName)) {
                        return true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }

    @Composable
    fun observeEth(ethState: EthState){
        ethState.eth0Enable.value = supportInterface("eth0")
        ethState.eth1Enable.value = supportInterface("eth1")

        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        val ethReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val info = intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
                info?.type.let {
                    if(it == ConnectivityManager.TYPE_ETHERNET){
                        ethState.eth0Connected.value = FileUtil.readInt("/sys/class/net/eth0/carrier") == 1
                        ethState.eth1Connected.value = FileUtil.readInt("/sys/class/net/eth1/carrier") == 1
                    }
                }
            }
        }

        DisposableEffect(LocalLifecycleOwner.current){

            val ethFilter = IntentFilter()
            ethFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

            context.registerReceiver(ethReceiver, ethFilter)

            val lifecycleObserver = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    context.unregisterReceiver(ethReceiver)
                }
            }

            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

            onDispose {
                context.unregisterReceiver(ethReceiver)
            }
        }
    }
}