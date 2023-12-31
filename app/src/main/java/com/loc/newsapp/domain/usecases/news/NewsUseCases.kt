package com.loc.newsapp.domain.usecases.news

import javax.inject.Inject

data class NewsUseCases (
    val getNews: GetNews,
    val searchNews: SearchNews,
    val upsetArticle: UpsetArticle,
    val deleteArticle: DeleteArticle,
    val selectArticles: SelectArticles,
    val selectArticle: SelectArticle
)