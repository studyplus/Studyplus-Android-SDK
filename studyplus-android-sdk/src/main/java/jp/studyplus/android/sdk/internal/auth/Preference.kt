package jp.studyplus.android.sdk.internal.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import jp.studyplus.android.sdk.BuildConfig

internal class Preference(context: Context) {

    private val preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    val isAuthenticated: Boolean
        get() = accessToken.isNotEmpty()

    val accessToken: String
        get() = preferences.getString(KEY_ACCESS_TOKEN, "") ?: ""

    fun update(data: Intent) {
        val code = data.getStringExtra(EXTRA_SP_AUTH_RESULT_CODE)
        val token = data.getStringExtra(EXTRA_SP_AUTH_ACCESS_TOKEN)
        if (code.isNullOrEmpty() || token.isNullOrEmpty()) {
            Log.e(
                "StudyplusSDK",
                "The data is invalid. If code is correct, please contact Studyplus Dev team.",
            )
            return
        }

        if (code != RESULT_CODE_AUTHENTICATED) {
            // not authenticated
            Log.e(
                "StudyplusSDK",
                "Your app is not authenticated.",
            )
            return
        }

        val editor = preferences.edit()
        editor.putString(KEY_ACCESS_TOKEN, data.getStringExtra(EXTRA_SP_AUTH_ACCESS_TOKEN))
        editor.apply()
    }

    fun remove() {
        val editor = preferences.edit()
        editor.putString(KEY_ACCESS_TOKEN, "")
        editor.apply()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val EXTRA_SP_AUTH_RESULT_CODE = "sp_auth_result_code"
        private const val EXTRA_SP_AUTH_ACCESS_TOKEN = "sp_auth_access_token"
        private const val RESULT_CODE_AUTHENTICATED = "AUTHENTICATED"

        private val prefName = "Certification:" + Uri.parse(BuildConfig.API_ENDPOINT).host
    }
}
