package jp.studyplus.android.sdk.internal.api

import android.content.Context
import io.reactivex.Observable
import jp.studyplus.android.sdk.internal.api.response.PostStudyRecordsResponse
import jp.studyplus.android.sdk.record.StudyRecord
import retrofit2.Retrofit

class ApiClient
constructor(retrofit: Retrofit) {

    companion object {
        val apiClient by lazy { ApiClient(ApiManager.retrofit) }
        lateinit var apiService: ApiService

        private fun getOAuthAccessToken(context: Context): Observable<String> {
            return Observable.just(CertificationStore.create(context))
                    .map { it.apiCertification.accessToken }
                    .map { "OAuth $it" }
        }
    }

    init {
        apiService = retrofit.create(ApiService::class.java)
    }

    fun postStudyRecords(context: Context, studyRecord: StudyRecord): Observable<PostStudyRecordsResponse> {
        return getOAuthAccessToken(context).flatMap { apiService.postStudyRecords(it, studyRecord) }
    }
}