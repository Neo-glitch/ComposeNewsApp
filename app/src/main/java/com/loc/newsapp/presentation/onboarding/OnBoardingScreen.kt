package com.loc.newsapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.loc.newsapp.presentation.Dimens.MediumPadding2
import com.loc.newsapp.presentation.common.NewsButton
import com.loc.newsapp.presentation.common.NewsTextButton
import com.loc.newsapp.presentation.onboarding.components.OnBoardingPage
import com.loc.newsapp.presentation.onboarding.components.PageIndicator
import kotlinx.coroutines.launch


// passed event lambda rather than passing viewModel for tasks
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
){

    Column(modifier = Modifier.fillMaxSize()) {
        // rememberPagerState is part of compose foundations api
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonState by remember{
            derivedStateOf {
                // gets a state base of calculation using another state
                // and get text for back and next button
                when(pagerState.currentPage){
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }
        
        HorizontalPager(state = pagerState) {index ->
            OnBoardingPage(page = pages[index])
        }
        
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                // adds padding to accommodate navigation bar
                // and used since onboarding screen will be behind system bars(e.g status bar)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            PageIndicator(modifier = Modifier.width(52.dp), pageSize = pages.size, selectedPage = pagerState.currentPage)

            // ensures launched coroutine is tied to this composable
            val scope = rememberCoroutineScope()
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End){
                if(buttonState[0].isNotEmpty()){
                    NewsTextButton(text = buttonState[0]) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    }
                }

                NewsButton(text = buttonState[1]) {
                    scope.launch {
                        if(pagerState.currentPage == 2){
                            // nav to main screen
                            event(OnBoardingEvent.SaveAppEntry)
                        } else {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}