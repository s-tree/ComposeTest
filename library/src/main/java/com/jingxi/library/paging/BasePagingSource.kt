package com.jingxi.library.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

public open class BasePagingSource<Value : Any>(
    private val pageStart:Int = 1,
    val loadData :suspend (pageIndex:Int) -> (List<Value>)) : PagingSource<Int,Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val pageIndex : Int = (params.key ?: pageStart)
        val response = try{
            loadData(pageIndex)
        }catch (exception:Exception){
            return LoadResult.Error(exception)
        }
        val nextPage = if(response.isEmpty()) {pageIndex} else {pageIndex + 1}
        Log.d("TestPagingActivity","pageIndex = $pageIndex nextPage = $nextPage")
        return LoadResult.Page(
            response,
            prevKey = if(pageIndex - 1 < pageStart) null else pageIndex - 1,
            nextKey = nextPage)
    }

//    abstract fun loadData(pageIndex:Int):List<Value>
}