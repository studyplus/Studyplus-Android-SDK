package jp.studyplus.android.sdk.internal.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import jp.studyplus.android.sdk.R

class AccessTokenNotFound : RuntimeException()

class AuthTransit
/**
 * @param consumerKey for API
 * @param consumerSecret for API
 */
constructor(
        private val consumerKey: String,
        private val consumerSecret: String
) {
    companion object {
        private const val EXTRA_CONSUMER_KEY = "consumer_key"
        private const val EXTRA_CONSUMER_SECRET = "consumer_secret"
    }

    fun start(activity: Activity, requestCode: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.putExtra(EXTRA_CONSUMER_KEY, consumerKey)
        intent.putExtra(EXTRA_CONSUMER_SECRET, consumerSecret)
        intent.data = Uri.parse(activity.getString(R.string.app_custom_scheme))
        activity.startActivityForResult(intent, requestCode)
    }
}
