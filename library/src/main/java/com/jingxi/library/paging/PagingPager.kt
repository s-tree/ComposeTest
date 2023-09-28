package com.jingxi.library.paging

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

fun <Value : Any> PagingPager(
    pageSize: Int = 10, pagerSource: PagingSource<Int, Value>
): Flow<PagingData<Value>> {
    val pagingConfig = PagingConfig(pageSize = pageSize, initialLoadSize = 2)
    val pager = Pager(config = pagingConfig) { pagerSource.javaClass.newInstance() }
        .flow.cachedIn(CoroutineScope(Dispatchers.Default))
    return pager
}

@Composable
fun <Value : Any> pagingStatus(
    pagingItems: LazyPagingItems<Value>,
    onRefresh: @Composable (refreshing: Boolean) -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onLoadFailed: @Composable (String?) -> Unit = {},
    onNoData: @Composable () -> Unit = {}
) {
    when (pagingItems.loadState.refresh) {
        is LoadState.Loading -> onRefresh(true)
        is LoadState.Error -> onRefresh(false)
        is LoadState.NotLoading -> onRefresh(false)
    }

    when (pagingItems.loadState.append) {
        is LoadState.Loading -> onLoading()
        is LoadState.Error -> if(pagingItems.itemCount != 0) {onLoadFailed((pagingItems.loadState.append as LoadState.Error).error.message)}
        else -> {}
    }

    if (pagingItems.loadState.refresh.endOfPaginationReached
        && pagingItems.loadState.append.endOfPaginationReached
        && pagingItems.itemCount == 0
    ) {
        onNoData()
    }
}

@Composable
fun <Value : Any> defaultLoadMoreViews(pagingItems: LazyPagingItems<Value>) {
    pagingStatus(
        pagingItems = pagingItems,
        onLoading = {
            Text(
                text = "正在加载",
                Modifier
                    .fillMaxWidth()
                    .height(88.dp), textAlign = TextAlign.Center
            )
        },
        onLoadFailed = {
            Text(
                text = it ?: "加载失败",
                Modifier
                    .fillMaxWidth()
                    .height(88.dp), textAlign = TextAlign.Center
            )
        },
        onNoData = {
            Text(
                text = "没有数据",
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), textAlign = TextAlign.Center
            )
        })
}