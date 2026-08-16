package com.lagru.topicgacha.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.lagru.topicgacha.model.TopicCategory

/**
 * App-specific Analytics events only.
 * Does not log topic text or any user-identifying custom IDs.
 */
class TopicAnalytics(
    private val firebaseAnalytics: FirebaseAnalytics,
) {
    fun logSelectCategory(category: TopicCategory) {
        log(EVENT_SELECT_CATEGORY, category)
    }

    fun logDrawTopic(category: TopicCategory) {
        log(EVENT_DRAW_TOPIC, category)
    }

    fun logFavoriteTopic(category: TopicCategory) {
        log(EVENT_FAVORITE_TOPIC, category)
    }

    fun logUnfavoriteTopic(category: TopicCategory) {
        log(EVENT_UNFAVORITE_TOPIC, category)
    }

    private fun log(eventName: String, category: TopicCategory) {
        val params = Bundle().apply {
            putString(PARAM_CATEGORY, category.displayName)
        }
        firebaseAnalytics.logEvent(eventName, params)
    }

    private companion object {
        const val EVENT_SELECT_CATEGORY = "select_category"
        const val EVENT_DRAW_TOPIC = "draw_topic"
        const val EVENT_FAVORITE_TOPIC = "favorite_topic"
        const val EVENT_UNFAVORITE_TOPIC = "unfavorite_topic"
        const val PARAM_CATEGORY = "category"
    }
}
