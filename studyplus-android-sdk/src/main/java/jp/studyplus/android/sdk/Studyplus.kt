package jp.studyplus.android.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import jp.studyplus.android.sdk.internal.api.Repository
import jp.studyplus.android.sdk.internal.auth.Preference
import jp.studyplus.android.sdk.internal.auth.Transit
import jp.studyplus.android.sdk.record.StudyRecord

class Studyplus(
    context: Context,
    private val consumerKey: String,
    private val consumerSecret: String,
) {

    private val store = Preference(context)

    /**
     * 設定されたConsumerKeyがすでに認証済みかどうかを判定
     *
     * @return true: 認証済み、 false: 未認証
     * @since 2.0.0
     */
    @SuppressWarnings("unused")
    fun isAuthenticated() = store.isAuthenticated

    /**
     * 設定されたConsumerKey, ConsumerSecretKeyによるStudyplus連携認証
     *
     * @since 2.0.0
     */
    @SuppressWarnings("unused")
    fun startAuth(activity: Activity, requestCode: Int) =
        Transit(consumerKey, consumerSecret).start(activity, requestCode)

    /**
     * [startAuth]の結果をStudyplusSDKに保存
     *
     * @since 2.0.0
     */
    @SuppressWarnings("unused")
    fun setAuthResult(data: Intent) = store.update(data)

    /**
     * Studyplus連携認証解除
     *
     * @since 2.6.1
     */
    @SuppressWarnings("unused")
    fun cancelAuth() = store.remove()

    /**
     * Studyplusとの認証情報を利用して学習記録をStudyplusへ投稿
     *
     * @since 2.0.0
     */
    @SuppressWarnings("unused")
    fun postRecord(record: StudyRecord, callback: PostCallback) {
        if (!isAuthenticated()) {
            callback.onFailure(StudyplusError.LoginRequired)
            return
        }

        Repository().post(
            store = store,
            record = record,
            callback = object : PostCallback {
                override fun onSuccess() {
                    runOnUiThread {
                        callback.onSuccess()
                    }
                }

                override fun onFailure(e: StudyplusError) {
                    runOnUiThread {
                        callback.onFailure(e)
                    }
                }
            })
    }

    private fun runOnUiThread(task: () -> Unit) {
        Handler(Looper.getMainLooper()).post { task() }
    }
}

interface PostCallback {
    fun onSuccess()
    fun onFailure(e: StudyplusError)
}
