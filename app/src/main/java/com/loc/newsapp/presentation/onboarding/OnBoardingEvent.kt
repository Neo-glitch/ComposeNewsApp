package com.loc.newsapp.presentation.onboarding

// ui events to be sent from ui to viewModel
// kinda like MVI stuff
sealed class OnBoardingEvent {

    object SaveAppEntry: OnBoardingEvent()
}