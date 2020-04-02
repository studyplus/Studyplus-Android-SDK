package jp.studyplus.android.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import jp.studyplus.android.sdk.internal.api.ApiClient
import jp.studyplus.android.sdk.internal.api.CertificationStore
import jp.studyplus.android.sdk.internal.auth.AuthTransit
import jp.studyplus.android.sdk.record.StudyRecord
import kotlinx.coroutines.runBlocking

class Studyplus private constructor() {

    private var consumerKey: String? = null
    private var consumerSecret: String? = null

    /**
     * ConsumerKey, ConsumerSecretKeyをStudyplus SDKに設定
     *
     * @since 2.0.0
     */
    fun setup(consumerKey: String, consumerSecret: String) {
        this.consumerKey = consumerKey
        this.consumerSecret = consumerSecret
    }

    /**
     * [setup]で設定されたConsumerKeyがすでに認証済みかどうかを判定
     *
     * @return true: 認証済み、 false: 未認証
     * @since 2.0.0
     */
    fun isAuthenticated(context: Context): Boolean =
            CertificationStore.create(context.applicationContext).isAuthenticated()

    /**
     * [setup]で設定されたConsumerKey, ConsumerSecretKeyによるStudyplus連携認証
     *
     * @since 2.0.0
     */
    fun startAuth(activity: Activity, requestCode: Int) {
        check(consumerKey != null && consumerSecret != null) { "Please call setup method before this method call." }

        AuthTransit(consumerKey!!, consumerSecret!!).start(activity, requestCode)
    }

    /**
     * [startAuth]の結果をStudyplusSDKに保存
     *
     * @since 2.0.0
     */
    fun setAuthResult(context: Context, data: Intent?) {
        if (data == null) {
            Log.e("StudyplusSDK", "The data is null. Please check your code. If the data that received from Studyplus app is null, please contact Studyplus Dev team.")
            return
        }

        CertificationStore.create(context.applicationContext).update(data)
    }

    /**
     * Studyplus連携認証解除
     *
     * @since 2.6.1
     */
    fun cancelAuth(context: Context) {
        CertificationStore.remove(context)
    }

    /**
     * Studyplusとの認証情報を利用して学習記録をStudyplusへ投稿
     *
     * @since 2.0.0
     */
    fun postRecord(context: Context, studyRecord: StudyRecord, listener: OnPostRecordListener?) {
        check(isAuthenticated(context)) { "Please check your application's authentication before this method call." }

        runBlocking {
            try {
                val deferred = ApiClient.postStudyRecords(context, studyRecord)
                val recordId = deferred.await()
                listener?.onResult(success = true, recordId = recordId)
            } catch (t: Throwable) {
                listener?.onResult(success = false, throwable = t)
            }
        }
    }

    companion object {
        interface OnPostRecordListener {
            fun onResult(success: Boolean, recordId: Long? = null, throwable: Throwable? = null)
        }

        @JvmStatic
        val instance by lazy { Studyplus() }
    }
}
