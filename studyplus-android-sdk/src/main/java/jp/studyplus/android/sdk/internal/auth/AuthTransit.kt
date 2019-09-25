package jp.studyplus.android.sdk.internal.auth

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
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
    }

    fun start(activity: AppCompatActivity, requestCode: Int) {
        if (isInstalledStudyplus(activity).not()) {
            showGooglePlayDialog(activity)
            return
        }

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.putExtra(EXTRA_CONSUMER_KEY, consumerKey)
        intent.putExtra(EXTRA_CONSUMER_SECRET, consumerSecret)
        intent.data = Uri.parse(activity.getString(R.string.app_custom_scheme))
        activity.startActivityForResult(intent, requestCode)
    }

    private fun showGooglePlayDialog(activity: AppCompatActivity) {
        val dialog = GooglePlayDialog()
        dialog.show(activity.supportFragmentManager, GooglePlayDialog.DIALOG_TAG)
    }

    private fun isInstalledStudyplus(context: Context): Boolean {
        val packages = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packages.any { it.packageName == "jp.studyplus.android.app" }
    }
}
