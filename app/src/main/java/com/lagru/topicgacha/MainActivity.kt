package com.lagru.topicgacha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lagru.topicgacha.data.FavoritesRepository
import com.lagru.topicgacha.data.TopicRepository
import com.lagru.topicgacha.ui.navigation.TopicGachaNavHost
import com.lagru.topicgacha.ui.theme.TopicGachaTheme
import com.lagru.topicgacha.viewmodel.TopicGachaViewModel
import com.lagru.topicgacha.viewmodel.TopicGachaViewModelFactory

class MainActivity : ComponentActivity() {

    private val topicRepository by lazy { TopicRepository() }
    private val favoritesRepository by lazy {
        FavoritesRepository(applicationContext, topicRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TopicGachaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: TopicGachaViewModel = viewModel(
                        factory = TopicGachaViewModelFactory(
                            topicRepository = topicRepository,
                            favoritesRepository = favoritesRepository,
                        ),
                    )
                    TopicGachaNavHost(viewModel = viewModel)
                }
            }
        }
    }
}
