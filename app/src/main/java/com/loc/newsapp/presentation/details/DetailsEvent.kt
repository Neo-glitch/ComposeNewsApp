package com.loc.newsapp.presentation.details

import com.loc.newsapp.domain.model.Article

sealed class DetailsEvent {

    // event to save or delete article
    data class UpsertDeleteArticle(val article: Article): DetailsEvent()
    object RemoveSideEffect: DetailsEvent()
}