package jp.studyplus.android.sdk.internal.api

import android.content.Context
import jp.studyplus.android.sdk.record.StudyRecord
import retrofit2.mock.MockRetrofit

internal class MockApiClient constructor(retrofit: MockRetrofit) {

    companion object {
        val apiClient by lazy { MockApiClient(MockApiManager.retrofit) }
        lateinit var apiService: MockApiService
    }

    init {
        val delegate = retrofit.create(ApiService::class.java)
        apiService = MockApiService(delegate)
    }

    fun postStudyRecords(context: Context?, studyRecord: StudyRecord) =
            apiService.postStudyRecords("", studyRecord)
}