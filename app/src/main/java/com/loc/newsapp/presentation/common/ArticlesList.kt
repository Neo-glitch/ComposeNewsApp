package com.loc.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.Dimens.MediumPadding1

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles : LazyPagingItems<Article>, // to work with paging Compose
    onCLick: (Article) -> Unit
){
    val handlePagingResult = HandlePagingResult(articles = articles)
    if(handlePagingResult){
        // data fetch success
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ){
          items(count = articles.itemCount){
              articles[it]?.let{article ->
                ArticleCard(article = article) {
                    onCLick(article)
                }
              }
          }

        }
    }
}

// handles state of our paging. If true then data loaded successfully else false
@Composable
fun HandlePagingResult(
    articles : LazyPagingItems<Article>
): Boolean{
    val loadState = articles.loadState // load state of pager

    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when{
        // first time loading, show shimmer effect
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen()
            false
        }

         else -> {
             true
         }

    }
}

@Composable
private fun ShimmerEffect(){

    Column (
        verticalArrangement = Arrangement.spacedBy(MediumPadding1)
    ){
        repeat(10){
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }

    }
}