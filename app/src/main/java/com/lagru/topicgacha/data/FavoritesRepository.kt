package com.lagru.topicgacha.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lagru.topicgacha.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.favoritesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "favorites",
)

class FavoritesRepository(
    private val context: Context,
    private val topicRepository: TopicRepository,
) {
    private val favoritesOrderKey = stringPreferencesKey("favorite_topic_ids_ordered")
    private val legacyFavoritesKey = stringSetPreferencesKey("favorite_topic_ids")

    val favorites: Flow<List<Topic>> = context.favoritesDataStore.data
        .map { preferences ->
            val orderedIds = parseOrderedIds(preferences[favoritesOrderKey])
            val ids = orderedIds.ifEmpty {
                preferences[legacyFavoritesKey].orEmpty().toList()
            }
            topicRepository.getTopicsByIds(ids)
        }
        .catch {
            emit(emptyList())
        }

    suspend fun addFavorite(topicId: String): Boolean =
        runCatching {
            context.favoritesDataStore.edit { preferences ->
                val current = parseOrderedIds(preferences[favoritesOrderKey]).ifEmpty {
                    preferences[legacyFavoritesKey].orEmpty().toList()
                }
                if (topicId in current) return@edit

                preferences[favoritesOrderKey] = encodeOrderedIds(current + topicId)
                preferences.remove(legacyFavoritesKey)
            }
        }.isSuccess

    suspend fun removeFavorite(topicId: String): Boolean =
        runCatching {
            context.favoritesDataStore.edit { preferences ->
                val current = parseOrderedIds(preferences[favoritesOrderKey]).ifEmpty {
                    preferences[legacyFavoritesKey].orEmpty().toList()
                }
                preferences[favoritesOrderKey] = encodeOrderedIds(current.filter { it != topicId })
                preferences.remove(legacyFavoritesKey)
            }
        }.isSuccess

    private fun parseOrderedIds(raw: String?): List<String> =
        raw
            ?.split(ID_SEPARATOR)
            ?.filter { it.isNotEmpty() }
            .orEmpty()

    private fun encodeOrderedIds(ids: List<String>): String =
        ids.joinToString(ID_SEPARATOR)

    private companion object {
        const val ID_SEPARATOR = ","
    }
}
