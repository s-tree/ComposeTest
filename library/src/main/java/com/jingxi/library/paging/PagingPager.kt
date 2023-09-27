package com.jingxi.library.paging

import androidx.compose.runtime.Composable
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

@Composable
fun <Value : Any> PagingPager(
    pageSize : Int = 10,pagerSource: PagingSource<Int,Value>): Flow<PagingData<Value>> {
    val pagingConfig = PagingConfig(pageSize = pageSize, initialLoadSize = 2)
    val pager = Pager(config = pagingConfig) {pagerSource.javaClass.newInstance()}
        .flow.cachedIn(CoroutineScope(Dispatchers.Default))
    return pager
}

@Composable
fun <Value : Any> pagingStatus(pagingItems: LazyPagingItems<Value>,
                 onRefresh : @Composable (refreshing : Boolean) -> Unit,
                 onLoading : @Composable () -> Unit,
                 onLoadFailed : @Composable () -> Unit,
                 onNoData : @Composable () -> Unit){
    when(pagingItems.loadState.refresh){
        is LoadState.Loading -> onRefresh(true)
        is LoadState.Error -> onRefresh(false)
        is LoadState.NotLoading -> onRefresh(false)
    }

    when(pagingItems.loadState.append){
        is LoadState.Loading -> onLoading()
        is LoadState.Error -> onLoadFailed()
        else -> {}
    }

    if(pagingItems.loadState.refresh.endOfPaginationReached
        && pagingItems.loadState.append.endOfPaginationReached
        && pagingItems.itemCount == 0){
        onNoData()
    }
}