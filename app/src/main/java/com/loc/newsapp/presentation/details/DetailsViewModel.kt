package com.loc.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.newStringBuilder
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    // handles what ever toast or alert we display to users
    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(url = event.article.url)
                    if(article == null){
                        // article not previously bookmarked
                        upsertArticle(event.article)
                    } else{
                        deleteArticle(event.article)
                    }
                }
            }
            DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article)
        sideEffect = "Article Deleted"
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsetArticle(article)
        sideEffect = "Article Saved"
    }

}

