package com.lagru.topicgacha.model

enum class TopicCategory(val displayName: String) {
    ALL("すべて"),
    FIRST_MEETING("初対面"),
    CASUAL("雑談"),
    DRINKING("飲み会"),
    LOVE("恋愛"),
    DEEP("深い話");

    companion object {
        val selectable: List<TopicCategory> = entries.filter { it != ALL }
    }
}
