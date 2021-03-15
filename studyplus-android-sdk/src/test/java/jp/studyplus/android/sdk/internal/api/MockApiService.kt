package jp.studyplus.android.sdk.internal.api

import jp.studyplus.android.sdk.PostCallback
import jp.studyplus.android.sdk.internal.createPostBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject

internal class MockApiService(private val client: OkHttpClient) {

    fun post(json: String, callback: PostCallback) {
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(JSONObject().apply { put("record_id", 9999L) }
            .toString()))
        server.start()

        val body = createPostBody(json)
        val request = Request.Builder()
            .url(server.url("/v1/study_records"))
            .post(body)
            .build()

        execute(client, request, callback)
    }
}