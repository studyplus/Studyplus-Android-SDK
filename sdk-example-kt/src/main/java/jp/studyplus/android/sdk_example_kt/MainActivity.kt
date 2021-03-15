package jp.studyplus.android.sdk_example_kt

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.studyplus.android.sdk.PostCallback
import jp.studyplus.android.sdk.Studyplus
import jp.studyplus.android.sdk.StudyplusError
import jp.studyplus.android.sdk.record.StudyRecord
import jp.studyplus.android.sdk.record.StudyRecordAmountTotal

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_AUTH = 1
    }

    private val instance by lazy {
        Studyplus(
            context = this,
            consumerKey = getString(R.string.consumer_key),
            consumerSecret = getString(R.string.consumer_secret),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.start_auth)?.apply {
            setOnClickListener {
                try {
                    instance.startAuth(this@MainActivity, REQUEST_CODE_AUTH)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Need for Studyplus 5.+", Toast.LENGTH_LONG).show()
                }
            }
        }

        findViewById<View>(R.id.cancel_auth)?.apply {
            setOnClickListener {
                instance.cancelAuth()
                updateAuthText()
            }
        }

        findViewById<View>(R.id.post_study_record)?.apply {
            setOnClickListener {
                val record = StudyRecord(
                    duration = 2 * 60,
                    amount = StudyRecordAmountTotal(30),
                    comment = "勉強した！！！"
                )
                instance.postRecord(record, object : PostCallback {
                    override fun onSuccess() {
                        Toast.makeText(context, "Post Study Record!!", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(e: StudyplusError) {
                        Toast.makeText(context, "error!!", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAuthText()
    }

    private fun updateAuthText() {
        val authStatusText = findViewById<TextView>(R.id.auth_status)
        authStatusText.text = when (instance.isAuthenticated()) {
            true -> getString(R.string.status_authenticated)
            else -> getString(R.string.status_unauthenticated)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            Toast.makeText(this, "error!!", Toast.LENGTH_LONG).show()
            return
        }

        when (requestCode) {
            REQUEST_CODE_AUTH -> {
                if (resultCode == Activity.RESULT_OK) {
                    instance.setAuthResult(data)
                    Toast.makeText(this, "Success!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}