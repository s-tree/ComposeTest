package com.jingxi.test_xiaorun.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import com.jingxi.library.paging.BasePagingSource
import com.jingxi.library.paging.PagingPager
import com.jingxi.test_xiaorun.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class RecentViewModel : ViewModel() {

    val pageItemFlow = PagingPager(pageSize = 10, pagerSource = RecentSource(), scope = viewModelScope)

    fun refresh(pageItem : LazyPagingItems<RecentBean>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(2000)
            }
            pageItem.refresh()
        }
    }
}

open class RecentSource : BasePagingSource<RecentBean>(1){
    override suspend fun loadData(pageIndex: Int): List<RecentBean> {
        delay(3000)
        val data = mutableListOf<RecentBean>()
        if(pageIndex == 1){
            for (i in 0..8){
                data.add(RecentBean(R.mipmap.icon_xitong,"系统消息$i","祝贺您加入到社区测试小区","2023-09-28 14:30"))
            }
        }
        return data
    }
}

data class RecentBean(val iconResource: Int,val title:String,val subTitle:String,val time:String)