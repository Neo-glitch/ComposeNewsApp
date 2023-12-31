package com.loc.newsapp.domain.usecases.news

import com.loc.newsapp.data.local.NewsDao
import com.loc.newsapp.domain.model.Article

class UpsetArticle(
    private val newsDao: NewsDao
) {

    // seems wrong, use cases shouldn't access dao
    // todo move db operations to the repository i.e data layer
    suspend operator fun invoke(article: Article){
        newsDao.upsert(article)
    }
}