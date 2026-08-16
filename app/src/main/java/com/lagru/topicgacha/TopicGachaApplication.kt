package com.lagru.topicgacha

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class TopicGachaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Avoid polluting production Analytics with debug sessions.
        FirebaseAnalytics.getInstance(this)
            .setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)

        // Crash reports are useful in both debug and release.
        FirebaseCrashlytics.getInstance()
            .isCrashlyticsCollectionEnabled = true
    }
}
