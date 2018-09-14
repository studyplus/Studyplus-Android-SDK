package jp.studyplus.android.sdk.internal.api

import io.reactivex.Observable
import jp.studyplus.android.sdk.internal.api.response.PostStudyRecordsResponse
import jp.studyplus.android.sdk.record.StudyRecord
import retrofit2.mock.BehaviorDelegate

internal class MockApiService(private val delegate: BehaviorDelegate<ApiService>) : ApiService {
    override fun postStudyRecords(oauth: String, studyRecord: StudyRecord): Observable<PostStudyRecordsResponse> {
        return delegate.returningResponse(PostStudyRecordsResponse(9999)).postStudyRecords(oauth, studyRecord)
    }
}
