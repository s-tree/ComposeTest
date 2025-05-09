package com.jingxi.test_xiaorun.ui.web

import android.net.Uri
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R
import java.io.File

@Composable
fun WebViewMain(navController: NavController,url:String){
    Column(
        Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()) {

        val title = remember {
            mutableStateOf("")
        }

        ConstraintLayout(
            Modifier
                .padding(start = 25.dp, end = 20.dp)
                .fillMaxWidth()
                .height(88.dp)) {

            val (backIcon) = createRefs()
            Image(painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(backIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(40.dp)
                    .clickable(onClick = { navController.popBackStack() }),
                contentScale = ContentScale.Fit)

            val space = createRef()
            Spacer(modifier = Modifier
                .constrainAs(space) {
                    start.linkTo(backIcon.end)
                }
                .width(10.dp))

            val backTv = createRef()
            Text(text = "返回",
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(backTv) {
                        start.linkTo(space.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable(onClick = { navController.popBackStack() }))

            val titleRes = createRef()
            Text(text = title.value,
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(titleRes){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
        }

        val u = if(url.startsWith("http")){
            url
        }else{
            Uri.fromFile(File(url)).toString()
        }
        val webView = rememberWebViewWithLifeCycle()
        AndroidView(
            factory = {
                webView
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            update = {
                it.layoutParams.height = LayoutParams.MATCH_PARENT
                it.settings.javaScriptEnabled = true
                it.settings.javaScriptCanOpenWindowsAutomatically = true
                it.settings.domStorageEnabled = true
                it.settings.loadsImagesAutomatically = true
                it.settings.mediaPlaybackRequiresUserGesture = false
                it.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                it.settings.pluginState = WebSettings.PluginState.ON
                it.settings.useWideViewPort = false
                it.settings.defaultTextEncodingName = "UTF-8"
                it.webViewClient = WebViewClient()
                it.webChromeClient = JXChromeClient(title)
                it.loadUrl(u)
            })
    }
}

@Composable
private fun rememberWebViewLifecycleObserver(webView: WebView) : LifecycleEventObserver{
    return remember(webView) {
        LifecycleEventObserver { _, event ->
            run {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> webView.onResume()
                    Lifecycle.Event.ON_PAUSE -> webView.onPause()
                    Lifecycle.Event.ON_DESTROY -> webView.destroy()
                    else -> Log.e("WebView", event.name)
                }
            }
        }
    }
}

@Composable
fun rememberWebViewWithLifeCycle() : WebView{
    val context = LocalContext.current
    val webView = remember{
        WebView(context)
    }
    val lifecycleObserver = rememberWebViewLifecycleObserver(webView = webView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle){
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return webView
}

class JXChromeClient (titleState : MutableState<String>): WebChromeClient() {
    private val titleState : MutableState<String>
    init {
        this.titleState = titleState
    }
    override fun onReceivedTitle(view: WebView?, title: String?) {
        titleState.value = checkNotNull(title){""}
    }

}