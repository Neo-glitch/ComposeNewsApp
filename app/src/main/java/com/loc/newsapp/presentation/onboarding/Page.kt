package com.loc.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

// details of each onboarding screen page
data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf<Page>(
    Page(
        title = "Lorem Ipsum is Simply dummy",
        description = "Lorem Ipsum is simply dummy tet of the printing and typesetting industry.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Lorem Ipsum is Simply dummy",
        description = "Lorem Ipsum is simply dummy tet of the printing and typesetting industry.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Lorem Ipsum is Simply dummy",
        description = "Lorem Ipsum is simply dummy tet of the printing and typesetting industry.",
        image = R.drawable.onboarding3
    )
)