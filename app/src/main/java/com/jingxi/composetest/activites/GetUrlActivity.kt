package com.jingxi.composetest.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class GetUrlActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = MainScope()

        setContent {
            MaterialTheme{
                var loginResult by remember {
                    mutableStateOf(false)
                }
                Column(Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        scope.launch {
                            val result = request()
                            loginResult = when (result){
                                is Result.Success<String> -> true
                                else -> false
                            }
                        }
                    },
                        Modifier
                            .fillMaxWidth()
                            .height(88.dp)) {
                        Text(text = "获取数据",
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                                textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    if(loginResult){
                        Success()
                    }
                    else{
                        Failed()
                    }
                }
            }
        }
    }

    @Composable
    fun Success(){
        Text(text = "获取数据成功", textAlign = TextAlign.Center)
    }

    @Composable
    fun Failed(){
        Text(text = "获取数据失败", textAlign = TextAlign.Center)
    }

    sealed class Result<out R>{
        data class Success<out T>(val data:T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    private suspend fun request() : Result<String>{
        return withContext(Dispatchers.IO){
            var done = false;
            val url = URL("https://api.house-keeper.cn/api/v2/romServer/checkVersion?sn=GS40K36G1111&type=pad")
            (url.openConnection() as? HttpURLConnection)?.run {
                url.openConnection().getInputStream()?.run {
                    val byteArray = ByteArray(2048)
                    inputStream.read(byteArray)
                    inputStream.close()
                    val result = String(bytes = byteArray)
                    if(result.isNotEmpty()){
                        done = true
                    }
                }
            }
            if(done){
                Result.Success("获取数据成功")
            }
            else{
                Result.Error(Exception("获取数据失败"))
            }
        }
    }
}