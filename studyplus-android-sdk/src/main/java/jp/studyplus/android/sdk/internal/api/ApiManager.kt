package jp.studyplus.android.sdk.internal.api

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal object ApiManager {
    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}