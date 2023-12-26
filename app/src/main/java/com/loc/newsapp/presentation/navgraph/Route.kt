package com.loc.newsapp.presentation.navgraph

// class holds all the route for screens on the app
// could also have used enum class with route to as an arg
sealed class Route (
    val route: String
){
    object OnBoardingScreen: Route(route = "onBoardingScreen")
    object HomeScreen : Route("homeScreen")
    object SearchScreen: Route("searchScreen")
    object BookMarkScreen: Route("bookmarkScreen")
    object DetailScreen: Route("detailScreen")

    // route for sub navigation
    object AppStartNavigation: Route("appStartNavigation")
    object NewsNavigation: Route("newsNavigation")
    object NewsNavigatorScreen: Route("newsNavigator")

}