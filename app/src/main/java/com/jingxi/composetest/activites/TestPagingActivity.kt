package com.jingxi.composetest.activites

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

public class TestPagingActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pageSize = 20;
        val pagingConfig = PagingConfig(pageSize = pageSize,initialLoadSize = 2)
        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                object : PagingSource<Int,String>(){
                    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
                        return null
                    }

                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
                        val pageIndex = params.key ?: 0
                        val response = try{
                            loadData(pageIndex)
                        }catch (exception:Exception){
                            return LoadResult.Error(exception)
                        }
                        val nextPage = if(response.size == 0) {pageIndex} else {pageIndex + 1}
                        Log.d("TestPagingActivity","pageIndex = $pageIndex nextPage = $nextPage")
                        return LoadResult.Page(
                            response,
                            prevKey = if(pageIndex - 1 < 0) null else pageIndex - 1,
                            nextKey = nextPage)
                    }
                }
            }
        ).flow.cachedIn(CoroutineScope(Dispatchers.Default))

        setContent {
            content(pager)
        }
    }
}

@Composable
fun content(pager: Flow<PagingData<String>>){
    val pagingItems = pager.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxWidth()){
            pagingItems.apply {
                items(pagingItems){
                    value ->
                    Text(text = "$value", Modifier.fillMaxWidth().height(80.dp), textAlign = TextAlign.Center)
                }

                when{
                    loadState.append is LoadState.Loading -> {
                        item { Text(text = "加载中...", textAlign = TextAlign.Center)}
                    }
                    loadState.append is LoadState.Error -> {
                        item { Text(text = "加载失败...", textAlign = TextAlign.Center)}
                    }
                    loadState.refresh is LoadState.Error -> {
                        item { Text(text = "刷新失败", textAlign = TextAlign.Center)}
                    }
                    loadState.refresh is LoadState.Loading -> {
                        item { Text(text = "刷新中", textAlign = TextAlign.Center)}
                    }
                }
            }
    }
}

suspend fun loadData(pageIndex:Int):List<String>{
    delay(1000)
    val list = mutableListOf<String>()
    val start = pageIndex * 20
    val end = start + 20
    for (i in start until end){
        list.add(i.toString())
    }
    return list
}