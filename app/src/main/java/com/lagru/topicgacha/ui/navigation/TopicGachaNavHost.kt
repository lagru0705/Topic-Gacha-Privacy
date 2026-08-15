package com.lagru.topicgacha.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lagru.topicgacha.model.TopicCategory
import com.lagru.topicgacha.ui.favorite.FavoriteScreen
import com.lagru.topicgacha.ui.home.HomeScreen
import com.lagru.topicgacha.ui.topic.TopicScreen
import com.lagru.topicgacha.viewmodel.TopicGachaViewModel

@Composable
fun TopicGachaNavHost(
    viewModel: TopicGachaViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val currentTopic by viewModel.currentTopic.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val isFavoritesLoaded by viewModel.isFavoritesLoaded.collectAsState()
    val isCurrentTopicFavorite by viewModel.isCurrentTopicFavorite.collectAsState()
    var isDrawingTopic by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                selectedCategory = selectedCategory,
                isDrawEnabled = !isDrawingTopic,
                onCategorySelected = viewModel::selectCategory,
                onDrawTopic = {
                    if (isDrawingTopic) return@HomeScreen
                    isDrawingTopic = true
                    if (viewModel.drawTopic()) {
                        navController.navigate(Routes.TOPIC) {
                            launchSingleTop = true
                        }
                    }
                    isDrawingTopic = false
                },
                onNavigateToFavorites = {
                    navController.navigate(Routes.FAVORITES) {
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(Routes.TOPIC) {
            val topic = currentTopic
            if (topic == null) {
                LaunchedEffect(Unit) {
                    navController.popBackStack(Routes.HOME, inclusive = false)
                }
            } else {
                val categoryName = if (selectedCategory == TopicCategory.ALL) {
                    "すべて · ${topic.category.displayName}"
                } else {
                    selectedCategory.displayName
                }

                TopicScreen(
                    categoryName = categoryName,
                    topicText = topic.text,
                    isFavorite = isCurrentTopicFavorite,
                    onRedraw = viewModel::redrawTopic,
                    onToggleFavorite = viewModel::toggleFavorite,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
        }

        composable(Routes.FAVORITES) {
            FavoriteScreen(
                favorites = favorites,
                isLoading = !isFavoritesLoaded,
                onRemoveFavorite = viewModel::removeFavorite,
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
