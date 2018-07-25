package jp.studyplus.android.sdk_example_kt

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import jp.studyplus.android.sdk.Studyplus
import jp.studyplus.android.sdk.record.StudyRecordBuilder

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_AUTH = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Studyplus.instance.setup(
                getString(R.string.consumer_key),
                getString(R.string.consumer_secret))

        findViewById<View>(R.id.start_auth)?.apply {
            setOnClickListener {
                try {
                    Studyplus.instance.startAuth(this@MainActivity, REQUEST_CODE_AUTH)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Need for Studyplus 2.14.0+", Toast.LENGTH_LONG).show()
                }
            }
        }

        findViewById<View>(R.id.post_study_record)?.apply {
            setOnClickListener {
                val record = StudyRecordBuilder()
                        .setComment("勉強した！！！")
                        .setAmountTotal(30)
                        .setDurationSeconds(2 * 60)
                        .build()
                Studyplus.instance.postRecord(this@MainActivity, record,
                        object : Studyplus.Companion.OnPostRecordListener {
                            override fun onResult(success: Boolean, recordId: Long?, throwable: Throwable?) {
                                if (success) {
                                    Toast.makeText(context, "Post Study Record!! ($recordId)", Toast.LENGTH_LONG).show()
                                } else {
                                    throwable?.apply {
                                        Toast.makeText(context, "error!!", Toast.LENGTH_LONG).show()
                                        printStackTrace()
                                    }
                                }
                            }
                        })
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val authStatusText = findViewById<TextView>(R.id.auth_status)
        authStatusText.text = when(Studyplus.instance.isAuthenticated(this)) {
            true -> getString(R.string.status_authenticated)
            else -> getString(R.string.status_unauthenticated)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_AUTH -> {
                if (resultCode == Activity.RESULT_OK) {
                    Studyplus.instance.setAuthResult(this, data)
                    Toast.makeText(this@MainActivity, "Success!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}