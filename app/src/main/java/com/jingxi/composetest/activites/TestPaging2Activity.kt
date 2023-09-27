package com.jingxi.composetest.activites

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jingxi.library.paging.BasePagingSource
import com.jingxi.library.paging.PagingPager
import com.jingxi.library.paging.pagingStatus
import com.jingxi.library.weiget.PullRefreshAnimLayout
import kotlinx.coroutines.delay

public class TestPaging2Activity : ComponentActivity(){
    val pageSize = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            content()
        }
    }

    @Composable
    fun content(){
        val pageItem = PagingPager(pageSize = pageSize,pagerSource = TestSource()).collectAsLazyPagingItems()

        val refreshState = remember{
            mutableStateOf(false)
        }

        PullRefreshAnimLayout(refreshing = refreshState.value, onRefresh = {
            Log.d("TestPaging2Activity","start refresh")
            pageItem.refresh()
        }) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()){
                pageItem.apply {
                    items(items = pageItem){
                            value ->
                        Text(text = "$value",
                            Modifier
                                .fillMaxWidth()
                                .height(80.dp), textAlign = TextAlign.Center)
                    }

                    item{
                        pagingStatus(
                            pagingItems = pageItem,
                            onRefresh = {refreshing -> run { Log.d("TestPaging2Activity","onRefresh $refreshing") } },
                            onLoading = { Text(text = "正在加载",
                                Modifier
                                    .fillMaxWidth()
                                    .height(88.dp), textAlign = TextAlign.Center) },
                            onLoadFailed = { Text(text = "加载失败",
                                Modifier
                                    .fillMaxWidth()
                                    .height(88.dp), textAlign = TextAlign.Center)},
                            onNoData = {Text(text = "没有数据",
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(), textAlign = TextAlign.Center)})
                    }
                }
            }
        }
    }

}

open class TestSource :BasePagingSource<String>(1) {

    override suspend fun loadData(pageIndex:Int): List<String> {
        delay(1000)
        val list = mutableListOf<String>()
        val start = pageIndex * 20
        val end = start + 20
        for (i in start until end){
            list.add(i.toString())
        }
        return list
    }
}
