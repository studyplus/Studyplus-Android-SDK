package jp.studyplus.android.sdk.internal.api

import android.content.Context
import jp.studyplus.android.sdk.record.StudyRecord

internal object MockApiClient {
    fun postStudyRecords(context: Context?, studyRecord: StudyRecord, callback: PostCallback) =
            MockApiService(ApiManager.client).post(studyRecord.toJson(), callback)
}