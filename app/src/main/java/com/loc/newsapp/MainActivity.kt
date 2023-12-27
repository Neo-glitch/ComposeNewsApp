package com.loc.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.loc.newsapp.presentation.navgraph.NavGraph
import com.loc.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject lateinit var appEntryUseCases: AppEntryUseCases
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // makes screen to overlap status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            // keeps splash alive until condition is false
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }

        // can do this since outside a composable screen
        // else this:
//        val lifecycle = LocalLifecycleOwner.current.lifecycle
//        val context = LocalContext.current
//        LaunchedEffect(key1 = lifecycle) {
//            // launches this whenever lifecycle object changes
//            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
//                appEntryUseCases.readAppEntry().collectLatest {
//
//                }
//            }
//        }
//        after adding this lib to gradle:  implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//        lifecycleScope.launch {
//            appEntryUseCases.readAppEntry().collectLatest {
//                Log.d("test", it.toString())
//            }
//        }

        // to setup splash screen
        setContent {
            NewsAppTheme {

                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    // runs after every composition completes
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Surface(color = MaterialTheme.colorScheme.background) {
                    // creates a viewModel tied to this activity
//                    val viewModel: OnBoardingViewModel = hiltViewModel()
//                    OnBoardingScreen(){viewModel.onEvent(it) }
                    val startDestination = viewModel.startDestination
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}
