package jp.studyplus.android.sdk.internal.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.net.toUri
import jp.studyplus.android.sdk.R

internal class AuthTransit
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

        private val URI_STORE = "market://details?id=jp.studyplus.android.app".toUri()
        private val INTENT_STORE = Intent(Intent.ACTION_VIEW, URI_STORE)
    }

    fun start(activity: Activity, requestCode: Int) {
        if (isInstalledStudyplus(activity).not()) {
            openGooglePlayStore(activity)
            return
        }

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.putExtra(EXTRA_CONSUMER_KEY, consumerKey)
        intent.putExtra(EXTRA_CONSUMER_SECRET, consumerSecret)
        intent.data = Uri.parse(activity.getString(R.string.app_custom_scheme))
        activity.startActivityForResult(intent, requestCode)
    }

    private fun openGooglePlayStore(context: Context) {
        context.startActivity(INTENT_STORE)
    }

    private fun isInstalledStudyplus(context: Context): Boolean {
        val packages = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packages.any { it.packageName == "jp.studyplus.android.app" }
    }
}
