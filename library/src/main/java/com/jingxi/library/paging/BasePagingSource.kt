package com.jingxi.library.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

public abstract class BasePagingSource<Value : Any>(
    private val pageStart:Int = 1) : PagingSource<Int,Value>() {

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

    abstract suspend fun loadData(pageIndex:Int):List<Value>
}