package com.loc.newsapp.domain.repository

import androidx.paging.PagingData
import com.loc.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    // rather than using paging source
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
}