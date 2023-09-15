package com.jingxi.test_xiaorun.ui.web

import android.webkit.WebChromeClient
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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jingxi.test_xiaorun.R

@Composable
fun WebViewMain(navController: NavController,url:String){
    Column(
        Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()) {

        var title = remember {
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

        AndroidView(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            factory = { context ->
                val webView = WebView(context)
                webView.settings.javaScriptEnabled = true
                webView.settings.javaScriptCanOpenWindowsAutomatically = true
                webView.settings.domStorageEnabled = true
                webView.settings.loadsImagesAutomatically = true
                webView.settings.mediaPlaybackRequiresUserGesture = false
                webView.webViewClient = WebViewClient()
                webView.webChromeClient = JXChromeClient(title)
                webView.loadUrl(url)
                webView
            })
    }
}

open class JXChromeClient (val titleState : State<String>): WebChromeClient() {

    override fun onReceivedTitle(view: WebView?, title: String?) {
        titleState.value = checkNotNull(title){""}
    }

}