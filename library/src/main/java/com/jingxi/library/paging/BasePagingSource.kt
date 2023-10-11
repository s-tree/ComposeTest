package com.jingxi.library.paging

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

        if(response.isEmpty()){
            return LoadResult.Error<Int, Value>(NoMoreException("没有更多"))
        }

        return LoadResult.Page(
            response,
            null,
            nextKey = pageIndex + 1)
    }

    abstract suspend fun loadData(pageIndex:Int):List<Value>

    open fun newObject() : BasePagingSource<Value>{
        return this.javaClass.newInstance()
    }
}