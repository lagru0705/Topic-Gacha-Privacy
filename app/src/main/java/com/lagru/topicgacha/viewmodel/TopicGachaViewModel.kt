package com.lagru.topicgacha.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.lagru.topicgacha.analytics.TopicAnalytics
import com.lagru.topicgacha.data.FavoritesRepository
import com.lagru.topicgacha.data.TopicRepository
import com.lagru.topicgacha.model.Topic
import com.lagru.topicgacha.model.TopicCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class TopicGachaViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val topicRepository: TopicRepository,
    private val favoritesRepository: FavoritesRepository,
    private val topicAnalytics: TopicAnalytics,
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(restoreCategory())
    val selectedCategory: StateFlow<TopicCategory> = _selectedCategory.asStateFlow()

    private val _currentTopic = MutableStateFlow(restoreTopic())
    val currentTopic: StateFlow<Topic?> = _currentTopic.asStateFlow()

    val favorites: StateFlow<List<Topic>> = favoritesRepository.favorites
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    val isFavoritesLoaded: StateFlow<Boolean> = favoritesRepository.favorites
        .map { true }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val isCurrentTopicFavorite: StateFlow<Boolean> = combine(
        _currentTopic,
        favorites,
        isFavoritesLoaded,
    ) { topic, favList, loaded ->
        loaded && topic != null && favList.any { it.id == topic.id }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )

    private val favoriteToggleInProgress = AtomicBoolean(false)

    fun selectCategory(category: TopicCategory) {
        _selectedCategory.value = category
        savedStateHandle[KEY_SELECTED_CATEGORY] = category.name
        topicAnalytics.logSelectCategory(category)
    }

    fun drawTopic(): Boolean {
        val category = _selectedCategory.value
        val topic = topicRepository.getRandomTopic(
            category = category,
            excludeId = _currentTopic.value?.id,
        )
        updateCurrentTopic(topic)
        if (topic != null) {
            topicAnalytics.logDrawTopic(category)
        }
        return topic != null
    }

    fun redrawTopic() {
        val category = _selectedCategory.value
        val current = _currentTopic.value
        val topic = topicRepository.getRandomTopic(
            category = category,
            excludeId = current?.id,
        )
        updateCurrentTopic(topic)
        if (topic != null) {
            topicAnalytics.logDrawTopic(category)
        }
    }

    fun toggleFavorite() {
        val topic = _currentTopic.value ?: return
        if (!isFavoritesLoaded.value) return
        if (!favoriteToggleInProgress.compareAndSet(false, true)) return

        viewModelScope.launch {
            try {
                val currentlyFavorite = favorites.value.any { it.id == topic.id }
                if (currentlyFavorite) {
                    if (favoritesRepository.removeFavorite(topic.id)) {
                        topicAnalytics.logUnfavoriteTopic(topic.category)
                    }
                } else {
                    if (favoritesRepository.addFavorite(topic.id)) {
                        topicAnalytics.logFavoriteTopic(topic.category)
                    }
                }
            } finally {
                favoriteToggleInProgress.set(false)
            }
        }
    }

    fun removeFavorite(topicId: String) {
        val topic = topicRepository.getTopicById(topicId)
        viewModelScope.launch {
            if (favoritesRepository.removeFavorite(topicId)) {
                topic?.let { topicAnalytics.logUnfavoriteTopic(it.category) }
            }
        }
    }

    private fun updateCurrentTopic(topic: Topic?) {
        _currentTopic.value = topic
        if (topic != null) {
            savedStateHandle[KEY_CURRENT_TOPIC_ID] = topic.id
        } else {
            savedStateHandle.remove<String>(KEY_CURRENT_TOPIC_ID)
        }
    }

    private fun restoreCategory(): TopicCategory {
        val name = savedStateHandle.get<String>(KEY_SELECTED_CATEGORY) ?: return TopicCategory.ALL
        return runCatching { TopicCategory.valueOf(name) }.getOrDefault(TopicCategory.ALL)
    }

    private fun restoreTopic(): Topic? {
        val topicId = savedStateHandle.get<String>(KEY_CURRENT_TOPIC_ID) ?: return null
        return topicRepository.getTopicById(topicId)
    }

    private companion object {
        const val KEY_SELECTED_CATEGORY = "selected_category"
        const val KEY_CURRENT_TOPIC_ID = "current_topic_id"
    }
}

class TopicGachaViewModelFactory(
    private val topicRepository: TopicRepository,
    private val favoritesRepository: FavoritesRepository,
    private val topicAnalytics: TopicAnalytics,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(TopicGachaViewModel::class.java)) {
            return TopicGachaViewModel(
                savedStateHandle = extras.createSavedStateHandle(),
                topicRepository = topicRepository,
                favoritesRepository = favoritesRepository,
                topicAnalytics = topicAnalytics,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
