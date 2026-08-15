package com.lagru.topicgacha.data

import com.lagru.topicgacha.model.Topic
import com.lagru.topicgacha.model.TopicCategory

class TopicRepository {

    fun getRandomTopic(category: TopicCategory, excludeId: String? = null): Topic? {
        val pool = topicsFor(category)
        val candidates = excludeId?.let { id -> pool.filter { it.id != id } } ?: pool
        return candidates.randomOrNull() ?: pool.randomOrNull()
    }

    fun getTopicById(id: String): Topic? = LocalTopics.all.find { it.id == id }

    fun getTopicsByIds(ids: List<String>): List<Topic> =
        ids.mapNotNull { getTopicById(it) }

    private fun topicsFor(category: TopicCategory): List<Topic> =
        if (category == TopicCategory.ALL) {
            LocalTopics.all
        } else {
            LocalTopics.all.filter { it.category == category }
        }
}
