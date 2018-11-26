package jp.studyplus.android.sdk.internal.api

import jp.studyplus.android.sdk.BuildConfig
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

internal class ApiService(private val client: OkHttpClient) {
    fun post(auth: String, json: String): Deferred<Long?> {
        val body = createPostBody(json)
        val request = Request.Builder()
                .header("Accept", HEADER_JSON)
                .header("Content-type", HEADER_JSON)
                .addHeader("Authorization", auth)
                .url("$ENDPOINT/v1/study_records")
                .post(body)
                .build()

        return execute(client.newCall(request))
    }

    companion object {
        private const val ENDPOINT = BuildConfig.API_ENDPOINT
        private const val HEADER_JSON = "application/json"
    }
}

private val JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8")

internal fun createPostBody(json: String) =
        RequestBody.create(JSON_MEDIA_TYPE, json)

internal fun execute(call: Call): Deferred<Long?> {
    val deferred = CompletableDeferred<Long?>()
    deferred.invokeOnCompletion {
        if (deferred.isCancelled) {
            call.cancel()
        }
    }

    call.enqueue(object : Callback {
        private val EMPTY_JSON = "{}"
        private val RECORD_ID = "record_id"
        private val INVALID_RECORD_ID = -1L

        override fun onFailure(call: Call, e: IOException) {
            deferred.completeExceptionally(e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val parsedJson = JSONObject(response.body()?.string() ?: EMPTY_JSON)
                val recordId = parsedJson.optLong(RECORD_ID, INVALID_RECORD_ID)

                if (recordId != INVALID_RECORD_ID) {
                    deferred.complete(recordId)
                } else {
                    deferred.complete(null)
                }
            } else {
                deferred.completeExceptionally(IOException(response.toString()))
            }
        }
    })

    return deferred
}
