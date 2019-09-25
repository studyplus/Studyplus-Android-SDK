package jp.studyplus.android.sdk.internal.auth

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import jp.studyplus.android.sdk.R

internal class GooglePlayDialog : DialogFragment() {

    companion object {
        const val DIALOG_TAG = "fragment_tag_google_play"
        private val URI_STORE = "market://details?id=jp.studyplus.android.app".toUri()
        private val INTENT_STORE = Intent(Intent.ACTION_VIEW, URI_STORE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: return super.onCreateDialog(savedInstanceState)

        val builder = AlertDialog.Builder(activity)
        builder
            .setTitle(getString(R.string.google_play_dialog_title))
            .setMessage(R.string.google_play_dialog_message)
            .setPositiveButton(getString(R.string.google_play_dialog_text_store)) { _, _ ->
                context?.startActivity(INTENT_STORE)
            }
            .setNegativeButton(getString(R.string.google_play_dialog_text_cancel)) { _, _ ->
                // nop
            }

        return builder.create()
    }
}