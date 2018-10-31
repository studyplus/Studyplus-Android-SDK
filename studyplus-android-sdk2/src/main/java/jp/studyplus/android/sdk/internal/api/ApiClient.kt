package jp.studyplus.android.sdk.internal.api

import android.content.Context
import jp.studyplus.android.sdk.internal.api.response.PostStudyRecordsResponse
import jp.studyplus.android.sdk.record.StudyRecord
import kotlinx.coroutines.Deferred

internal object ApiClient {
    private val apiService by lazy { ApiManager.retrofit.create(ApiService::class.java) }

    fun postStudyRecords(context: Context, studyRecord: StudyRecord): Deferred<PostStudyRecordsResponse> {
        return apiService.postStudyRecords(CertificationStore.create(context).getOAuthAccessToken(), studyRecord)
    }
}
