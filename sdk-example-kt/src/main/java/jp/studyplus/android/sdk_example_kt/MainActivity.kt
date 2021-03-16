package jp.studyplus.android.sdk_example_kt

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import jp.studyplus.android.sdk.PostCallback
import jp.studyplus.android.sdk.Studyplus
import jp.studyplus.android.sdk.StudyplusError
import jp.studyplus.android.sdk.record.StudyRecord
import jp.studyplus.android.sdk.record.StudyRecordAmountTotal
import jp.studyplus.android.sdk_example_kt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val REQUEST_CODE_AUTH = 112
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
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        binding.authStatus.text = currentAuthState()
                    }
                    else -> {
                        // nop
                    }
                }
            }
        })

        binding.startAuth.setOnClickListener {
            try {
                instance.startAuth(this@MainActivity, REQUEST_CODE_AUTH)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast
                    .makeText(this@MainActivity, "Need for Studyplus 5.+", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.cancelAuth.setOnClickListener {
            instance.cancelAuth()
            binding.authStatus.text = currentAuthState()
        }
        binding.postStudyRecord.setOnClickListener {
            val record = StudyRecord(
                duration = 2 * 60,
                amount = StudyRecordAmountTotal(30),
                comment = "勉強した！！！",
            )
            instance.postRecord(record, object : PostCallback {
                override fun onSuccess() {
                    Toast
                        .makeText(this@MainActivity, "Post Study Record!!", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onFailure(e: StudyplusError) {
                    Toast
                        .makeText(this@MainActivity, "error!!", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            Toast.makeText(this, "error!!", Toast.LENGTH_LONG).show()
            return
        }

        if (requestCode == REQUEST_CODE_AUTH) {
            instance.setAuthResult(data)
            Toast.makeText(this, "Success!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun currentAuthState() = when (instance.isAuthenticated()) {
        true -> getString(R.string.status_authenticated)
        else -> getString(R.string.status_unauthenticated)
    }
}