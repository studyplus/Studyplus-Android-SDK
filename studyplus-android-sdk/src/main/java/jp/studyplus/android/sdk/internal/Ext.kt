package jp.studyplus.android.sdk.internal

import jp.studyplus.android.sdk.record.StudyRecord
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

internal fun StudyRecord.requestBody() = json
    .toRequestBody("application/json; charset=utf-8".toMediaType())

internal fun createRequest(url: String, accessToken: String, body: RequestBody) =
    createRequest(url.toHttpUrl(), accessToken, body)

internal fun createRequest(url: HttpUrl, accessToken: String, body: RequestBody) =
    Request.Builder()
        .header("Accept", "application/json")
        .header("Content-type", "application/json")
        .header("Authorization", "OAuth $accessToken")
        .url(url)
        .post(body)
        .build()
