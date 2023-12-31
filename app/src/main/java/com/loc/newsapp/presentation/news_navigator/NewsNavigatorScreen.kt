package com.loc.newsapp.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.bookmark.BookmarkScreen
import com.loc.newsapp.presentation.bookmark.BookmarkViewModel
import com.loc.newsapp.presentation.details.DetailsEvent
import com.loc.newsapp.presentation.details.DetailsScreen
import com.loc.newsapp.presentation.details.DetailsViewModel
import com.loc.newsapp.presentation.home.HomeScreen
import com.loc.newsapp.presentation.home.HomeViewModel
import com.loc.newsapp.presentation.navgraph.Route
import com.loc.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.loc.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.loc.newsapp.presentation.search.SearchScreen
import com.loc.newsapp.presentation.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigatorScreen() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(R.drawable.ic_home, "home"),
            BottomNavigationItem(R.drawable.ic_search, "Search"),
            BottomNavigationItem(R.drawable.ic_bookmark, "Bookmark"),
        )
    }

    // for handling navigation between bottom nav pages
    val navController = rememberNavController()
    // state for getting current back stack of the nav controller
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookMarkScreen.route -> 2
            else -> 0
        }
    }

    var isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.BookMarkScreen.route
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTap(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTap(
                                navController = navController,
                                route = Route.BookMarkScreen.route
                            )
                        }
                    },
                )
            }

        }
    ) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTap(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                SearchScreen(
                    state = state.value, event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article = article)
                    },
                )
            }

            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()

                // todo handle side Effect
                if(viewModel.sideEffect != null){
                    val context = LocalContext.current
                    Toast.makeText(context, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }

                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(article = article, event = viewModel::onEvent) {
                            navController.navigateUp()
                        }
                    }
            }

            composable(route = Route.BookMarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    },
                )
            }
        }
    }
}

// handle navigation on tap on bottom nav items
private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                // saves state of screens popped
                this.saveState = true
            }
            // restore state of screen we are navigating to
            restoreState = true
            launchSingleTop =
                true // allows single instance of that screen no matter x amount of nav call
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    // way of passing parcelable to nextScreen, by passing it to save state handle
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(route = Route.DetailScreen.route)
}