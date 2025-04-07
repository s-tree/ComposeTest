package com.jingxi.composetest

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jingxi.composetest.ui.theme.ComposeTestTheme
import com.jingxi.composetest.util.UnitCache
import com.jingxi.composetest.util.tp
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val configuration = LocalConfiguration.current
            val screenWidthDp = configuration.screenWidthDp
            UnitCache.screenWidth = screenWidthDp
            UnitCache.designScale = UnitCache.designWidth.toFloat() / UnitCache.screenWidth.toFloat()

            Greeting(queryActivities())
        }
    }

    private fun queryActivities():List<TestItem>{
        var testItems = ArrayList<TestItem>()
        var packageInfo: PackageInfo? = packageManager.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES)

        for (activityInfo in packageInfo!!.activities) {
            if (activityInfo.name?.equals(javaClass.name) == true) {
                continue
            }
            var _class: Class<*>? = null
            try {
                _class = Class.forName(activityInfo.name)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            if (_class == null) {
                continue
            }
            testItems.add(TestItem(_class.simpleName, _class))
        }
        return testItems
    }

    @Composable
    fun Greeting(intents : List<TestItem>) {
        ComposeTestTheme {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp)) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
                    items(count = (intents.size / 3 + if (intents.size % 3 == 0) 0 else 1)){
                        val lineCount = it
                        LazyRow(userScrollEnabled = false,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)){
                            items(count = min(3,intents.size - lineCount * 3)) {
                                Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                modifier = Modifier.fillMaxWidth()){
                                    val position = lineCount * 3 + it
                                    Text(text = intents[position].name,
                                        Modifier
                                            .padding(15.dp)
                                            .clickable(onClick = {
                                                startActivity(
                                                    Intent(
                                                        this@MainActivity,
                                                        intents[position]._class
                                                    )
                                                )
                                            }),
                                        color = Color(red = 0x1a,green = 0x99,blue = 0x01)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Greeting(ArrayList())
    }
}

class TestItem(var name: String,var _class: Class<*>) {
}

@Preview
@Composable
fun Testui(){
    Text(
        text = "haha1",
        fontSize = 18.sp,
        modifier = Modifier.size(50.dp,50.dp).background(Color.Red))
    Text(
        text = "haha2",
        fontSize = 18.sp,
        modifier = Modifier.size(50.tp,50.tp).background(Color.Blue))
}