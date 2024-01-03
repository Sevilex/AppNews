package com.example.newsapp.data.api

import com.example.newsapp.data.AppConstans.API_KEY
import com.example.newsapp.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    //GET https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY

    @GET("v2/top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}