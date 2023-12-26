package com.loc.newsapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.loc.newsapp.domain.usecases.AppEntryUseCases
import com.loc.newsapp.presentation.navgraph.NavGraph
import com.loc.newsapp.presentation.onboarding.OnBoardingScreen
import com.loc.newsapp.presentation.onboarding.OnBoardingViewModel
import com.loc.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

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
