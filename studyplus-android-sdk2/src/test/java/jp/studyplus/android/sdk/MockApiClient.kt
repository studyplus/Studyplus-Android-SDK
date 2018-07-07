package jp.studyplus.android.sdk

import android.content.Context
import io.reactivex.Observable
import jp.studyplus.android.sdk.internal.api.ApiService
import jp.studyplus.android.sdk.internal.api.response.PostStudyRecordsResponse
import jp.studyplus.android.sdk.record.StudyRecord
import retrofit2.mock.MockRetrofit

class MockApiClient
constructor(retrofit: MockRetrofit) {

    companion object {
        val apiClient by lazy { MockApiClient(MockApiManager.retrofit) }
        lateinit var apiService: MockApiService
    }

    init {
        val delegate = retrofit.create(ApiService::class.java)
        apiService = MockApiService(delegate)
    }

    fun postStudyRecords(context: Context?, studyRecord: StudyRecord): Observable<PostStudyRecordsResponse> {
        return apiService.postStudyRecords("", studyRecord)
    }
}