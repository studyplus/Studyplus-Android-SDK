package jp.studyplus.android.sdk.internal.api

import jp.studyplus.android.sdk.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

internal interface PostCallback {
    fun onSuccess(recordId: Long?)
    fun onFailure(e: IOException)
}

internal class ApiService(private val client: OkHttpClient) {
    fun post(auth: String, json: String, callback: PostCallback) {
        val body = createPostBody(json)
        val request = Request.Builder()
            .header("Accept", HEADER_JSON)
            .header("Content-type", HEADER_JSON)
            .addHeader("Authorization", auth)
            .url("$ENDPOINT/v1/study_records")
            .post(body)
            .build()

        execute(client, request, callback)
    }

    companion object {
        private const val ENDPOINT = BuildConfig.API_ENDPOINT
        private const val HEADER_JSON = "application/json"
    }
}

private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaTypeOrNull()
private const val EMPTY_JSON = "{}"
private const val RECORD_ID = "record_id"
private const val INVALID_RECORD_ID = -1L

internal fun createPostBody(json: String) = json.toRequestBody(JSON_MEDIA_TYPE)

internal fun execute(client: OkHttpClient, request: Request, callback: PostCallback) {
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback.onFailure(e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                callback.onFailure(IOException(response.toString()))
                return
            }

            val recordId = parseResponse(response)
            if (recordId != INVALID_RECORD_ID) {
                callback.onSuccess(recordId)
            } else {
                callback.onSuccess(null)
            }
        }
    })
}

private fun parseResponse(response: Response): Long {
    val parsedJson = JSONObject(response.body?.string() ?: EMPTY_JSON)
    return parsedJson.optLong(RECORD_ID, INVALID_RECORD_ID)
}
