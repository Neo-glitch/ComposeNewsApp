package com.loc.newsapp.data.remote.dto


import com.loc.newsapp.domain.model.Article

data class NewsResponse(
    var status: String?,
    var totalResults: Int?,
    var articles: List<Article>?
)