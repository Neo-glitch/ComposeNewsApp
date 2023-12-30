package com.loc.newsapp.data.remote

import com.loc.newsapp.data.remote.dto.NewsResponse
import com.loc.newsapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // gets all News
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): NewsResponse
}