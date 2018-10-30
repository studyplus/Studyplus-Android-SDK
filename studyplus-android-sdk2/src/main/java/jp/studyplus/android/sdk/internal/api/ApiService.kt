package jp.studyplus.android.sdk.internal.api

import jp.studyplus.android.sdk.internal.api.response.PostStudyRecordsResponse
import jp.studyplus.android.sdk.record.StudyRecord
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface ApiService {
    @Headers(value = [
        "Accept: application/json",
        "Content-type: application/json"
    ])
    @POST("/v1/study_records")
    fun postStudyRecords(
            @Header("Authorization") oauth: String,
            @Body studyRecord: StudyRecord
    ): Deferred<PostStudyRecordsResponse>
}