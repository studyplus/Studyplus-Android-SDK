package jp.studyplus.android.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.studyplus.android.sdk.internal.auth.AuthTransit
import jp.studyplus.android.sdk.internal.api.ApiClient
import jp.studyplus.android.sdk.internal.api.notNull
import jp.studyplus.android.sdk.record.StudyRecord
import io.reactivex.schedulers.Schedulers
import jp.studyplus.android.sdk.internal.api.CertificationStore

class Studyplus
private constructor() {
    companion object {
        interface OnPostRecordListener {
            fun onResult(success: Boolean, recordId: Long?, throwable: Throwable?)
        }

        @JvmStatic
        val instance by lazy { Studyplus() }
    }

    private var consumerKey: String? = null
    private var consumerSecret: String? = null

    fun setup(consumerKey: String, consumerSecret: String) {
        this.consumerKey = consumerKey
        this.consumerSecret = consumerSecret
    }

    fun isAuthenticated(context: Context): Boolean {
        return CertificationStore.create(context).isAuthenticated()
    }

    fun startAuth(activity: Activity, requestCode: Int) {
        notNull(consumerKey, consumerSecret) { consumerKey, consumerSecret ->
            AuthTransit(consumerKey, consumerSecret).apply {
                start(activity, requestCode)
            }
        }
    }

    fun setAuthResult(context: Context, data: Intent?) {
        data?.also {
            CertificationStore.create(context).update(it)
        }
    }

    fun postRecord(context: Context, studyRecord: StudyRecord, listener: OnPostRecordListener?) {
        ApiClient.apiClient.postStudyRecords(context, studyRecord)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            listener?.apply {
                                onResult(true, response.recordId, null)
                            }
                        },
                        { throwable ->
                            listener?.apply {
                                onResult(false, null, throwable)
                            }
                        }
                )
    }
}