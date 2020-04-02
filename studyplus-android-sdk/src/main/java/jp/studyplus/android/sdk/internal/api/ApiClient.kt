package jp.studyplus.android.sdk.internal.api

import android.content.Context
import jp.studyplus.android.sdk.record.StudyRecord

internal object ApiClient {
    fun postStudyRecords(context: Context, studyRecord: StudyRecord, callback: PostCallback) =
        ApiService(ApiManager.client).post(
            CertificationStore.create(context).getOAuthAccessToken(),
            studyRecord.toJson(),
            callback
        )
}
