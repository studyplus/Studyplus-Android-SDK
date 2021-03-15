package jp.studyplus.android.sdk.internal.api

import jp.studyplus.android.sdk.BuildConfig
import jp.studyplus.android.sdk.PostCallback
import jp.studyplus.android.sdk.StudyplusError
import jp.studyplus.android.sdk.internal.auth.Preference
import jp.studyplus.android.sdk.record.StudyRecord
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

internal class Repository(private val client: OkHttpClient = Manager.client) {
    fun post(store: Preference, record: StudyRecord, callback: PostCallback) {
        val body = record
            .json()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .header("Accept", "application/json")
            .header("Content-type", "application/json")
            .header("Authorization", "OAuth ${store.accessToken}")
            .url("${BuildConfig.API_ENDPOINT}/v1/study_records")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(StudyplusError.IOException(e))
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    callback.onFailure(StudyplusError.byCode(response.code))
                    return
                }

                callback.onSuccess()
            }
        })
    }
}