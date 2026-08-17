package com.lagru.topicgacha

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class TopicGachaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // prdRelease only — keep dev/debug sessions out of production Analytics.
        FirebaseAnalytics.getInstance(this)
            .setAnalyticsCollectionEnabled(isProductionAnalyticsEnabled())

        FirebaseCrashlytics.getInstance()
            .isCrashlyticsCollectionEnabled = true
    }

    private fun isProductionAnalyticsEnabled(): Boolean =
        BuildConfig.ENVIRONMENT == "prd" && !BuildConfig.DEBUG
}
