package com.lagru.topicgacha.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.lagru.topicgacha.data.FavoritesRepository
import com.lagru.topicgacha.data.TopicRepository
import com.lagru.topicgacha.model.Topic
import com.lagru.topicgacha.model.TopicCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class TopicGachaViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val topicRepository: TopicRepository,
    private val favoritesRepository: FavoritesRepository,
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

    val isCurrentTopicFavorite: StateFlow<Boolean> = combine(_currentTopic, favorites) { topic, favList ->
        topic != null && favList.any { it.id == topic.id }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )

    private val favoriteToggleInProgress = AtomicBoolean(false)

    fun selectCategory(category: TopicCategory) {
        _selectedCategory.value = category
        savedStateHandle[KEY_SELECTED_CATEGORY] = category.name
    }

    fun drawTopic(): Boolean {
        val topic = topicRepository.getRandomTopic(_selectedCategory.value)
        updateCurrentTopic(topic)
        return topic != null
    }

    fun redrawTopic() {
        val current = _currentTopic.value
        val topic = topicRepository.getRandomTopic(
            category = _selectedCategory.value,
            excludeId = current?.id,
        )
        updateCurrentTopic(topic)
    }

    fun toggleFavorite() {
        val topic = _currentTopic.value ?: return
        if (!favoriteToggleInProgress.compareAndSet(false, true)) return

        viewModelScope.launch {
            try {
                val currentlyFavorite = favorites.value.any { it.id == topic.id }
                if (currentlyFavorite) {
                    favoritesRepository.removeFavorite(topic.id)
                } else {
                    favoritesRepository.addFavorite(topic.id)
                }
            } finally {
                favoriteToggleInProgress.set(false)
            }
        }
    }

    fun removeFavorite(topicId: String) {
        viewModelScope.launch {
            favoritesRepository.removeFavorite(topicId)
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
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(TopicGachaViewModel::class.java)) {
            return TopicGachaViewModel(
                savedStateHandle = extras.createSavedStateHandle(),
                topicRepository = topicRepository,
                favoritesRepository = favoritesRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
