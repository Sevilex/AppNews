package com.example.newsapp.ui.repository

import com.example.newsapp.ResourceState
import com.example.newsapp.data.datasource.NewsDataSource
import com.example.newsapp.data.entity.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Newsrepository @Inject constructor(
    private val newsDataSource: NewsDataSource
) {

    suspend fun getNewsHeadLine(country: String): Flow<ResourceState<NewsResponse>> {
        return flow<ResourceState<NewsResponse>> {
            emit(ResourceState.Loading())

            val response = newsDataSource.getNewsHeadline(country)

            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error fetching data"))
            }
        }.catch { e->
            emit(ResourceState.Error(e?.localizedMessage?:"Some error in flow"))
        }
    }
}

//suspend fun getNewsHeadLine(country:String):Response<NewsRepository>{
//      return newsDataSource.getNewsHeadline(country)
//  }
