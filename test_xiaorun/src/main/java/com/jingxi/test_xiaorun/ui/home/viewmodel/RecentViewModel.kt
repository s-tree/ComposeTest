package com.jingxi.test_xiaorun.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import com.jingxi.library.paging.PagingPager
import com.jingxi.test_xiaorun.ui.home.RecentBean
import com.jingxi.test_xiaorun.ui.home.RecentSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class RecentViewModel : ViewModel() {

    val pageItemFlow = PagingPager(pageSize = 10, pagerSource = RecentSource())

    fun refresh(pageItem : LazyPagingItems<RecentBean>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(2000)
            }
            pageItem.refresh()
        }
    }
}